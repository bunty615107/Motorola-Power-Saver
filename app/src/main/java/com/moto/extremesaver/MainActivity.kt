package com.moto.extremesaver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.moto.extremesaver.domain.usecase.FetchBatteryStatsUseCase
import com.moto.extremesaver.power.PowerSettingsModifier
import com.moto.extremesaver.receiver.BatteryChangedReceiver
import com.moto.extremesaver.security.AuthManager
import com.moto.extremesaver.ui.screens.*
import com.moto.extremesaver.ui.theme.ExtremeSaverTheme
import com.moto.extremesaver.ui.viewmodel.AllowlistViewModel
import com.moto.extremesaver.ui.viewmodel.BatteryDashboardViewModel
import com.moto.extremesaver.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Screen {
    HOME, BATTERY, ALLOWLIST, SETTINGS
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var powerSettingsModifier: PowerSettingsModifier
    private lateinit var authManager: AuthManager
    private lateinit var batteryReceiver: BatteryChangedReceiver

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val allowlistViewModel: AllowlistViewModel by viewModels()
    private val batteryViewModel: BatteryDashboardViewModel by viewModels()

    @Inject lateinit var fetchBatteryStatsUseCase: FetchBatteryStatsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        powerSettingsModifier = PowerSettingsModifier(this)
        authManager = AuthManager(this)

        checkAndRequestWriteSettings()
        registerBatteryReceiver()

        setContent {
            ExtremeSaverTheme {
                var currentScreen by remember { mutableStateOf(Screen.HOME) }

                when (currentScreen) {
                    Screen.HOME -> HomeDashboardScreen(
                        viewModel = dashboardViewModel,
                        onPhoneClick = { openDialer() },
                        onSmsClick = { openSms() },
                        onExitClick = { exitExtremeMode() },
                        onBatteryClick = { currentScreen = Screen.BATTERY },
                        onAllowlistClick = { currentScreen = Screen.ALLOWLIST },
                        onSettingsClick = { currentScreen = Screen.SETTINGS }
                    )
                    Screen.BATTERY -> BatteryDashboardScreen(
                        viewModel = batteryViewModel,
                        onBackClick = { currentScreen = Screen.HOME }
                    )
                    Screen.ALLOWLIST -> AllowlistManagerScreen(
                        viewModel = allowlistViewModel,
                        onBackClick = { currentScreen = Screen.HOME }
                    )
                    Screen.SETTINGS -> SettingsScreen(
                        onBackClick = { currentScreen = Screen.HOME },
                        onAccessibilityClick = {
                            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                        },
                        onNotificationAccessClick = {
                            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                        },
                        onWriteSettingsClick = {
                            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                                data = Uri.parse("package:$packageName")
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }

        // Activate extreme mode on launch via the use case
        dashboardViewModel.enableExtremeMode()
    }

    private fun registerBatteryReceiver() {
        batteryReceiver = BatteryChangedReceiver { level, isCharging, temperature ->
            lifecycleScope.launch {
                fetchBatteryStatsUseCase.record(
                    level = level,
                    isCharging = isCharging,
                    temperature = temperature,
                    modeActive = true
                )
            }
        }
        registerReceiver(batteryReceiver, BatteryChangedReceiver.createIntentFilter())
    }

    private fun checkAndRequestWriteSettings() {
        if (!Settings.System.canWrite(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
        } else {
            powerSettingsModifier.enableExtremePowerSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Settings.System.canWrite(this)) {
            powerSettingsModifier.enableExtremePowerSettings()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(batteryReceiver)
        } catch (_: Exception) { }
    }

    private fun openDialer() {
        val intent = Intent(Intent.ACTION_DIAL)
        startActivity(intent)
    }

    private fun openSms() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_MESSAGING)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback
        }
    }

    private fun exitExtremeMode() {
        authManager.authenticate(
            onSuccess = {
                dashboardViewModel.disableExtremeMode()
                powerSettingsModifier.restoreStandardPowerSettings()
                val intent = Intent(Settings.ACTION_HOME_SETTINGS)
                startActivity(intent)
                finish()
            },
            onError = {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Override back button to prevent exiting launcher
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Do nothing to keep user in launcher
    }
}
