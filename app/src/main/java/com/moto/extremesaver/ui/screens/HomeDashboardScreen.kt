package com.moto.extremesaver.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.extremesaver.mode.Mode
import com.moto.extremesaver.ui.components.AppIconGrid
import com.moto.extremesaver.ui.components.BatteryStatus
import com.moto.extremesaver.ui.components.DigitalClock
import com.moto.extremesaver.ui.theme.ElectricBlue
import com.moto.extremesaver.ui.theme.LimeGreen
import com.moto.extremesaver.ui.viewmodel.DashboardUiState
import com.moto.extremesaver.ui.viewmodel.DashboardViewModel

@Composable
fun HomeDashboardScreen(
    viewModel: DashboardViewModel? = null,
    onPhoneClick: () -> Unit,
    onSmsClick: () -> Unit,
    onExitClick: () -> Unit,
    onBatteryClick: (() -> Unit)? = null,
    onAllowlistClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()?.value ?: DashboardUiState() // ⚡ Bolt: Use collectAsStateWithLifecycle to prevent background flow collection and UI recomposition when not visible

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Mode status indicator
        AnimatedVisibility(
            visible = uiState.mode == Mode.EXTREME_SAVER,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(LimeGreen.copy(alpha = 0.15f), ElectricBlue.copy(alpha = 0.15f))
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "⚡ EXTREME SAVER ACTIVE",
                    color = LimeGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DigitalClock()

        Spacer(modifier = Modifier.height(48.dp))

        BatteryStatus(
            batteryLevel = uiState.batteryLevel,
            isCharging = uiState.isCharging,
            estimatedHours = uiState.estimatedHours
        )

        // Drain rate indicator
        if (uiState.drainRatePerHour > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "%.1f%%/hr drain rate".format(uiState.drainRatePerHour),
                color = Color.Gray,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Error message
        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color(0xFFFF4444),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        AppIconGrid(
            onPhoneClick = onPhoneClick,
            onSmsClick = onSmsClick,
            onExitClick = onExitClick,
            onBatteryClick = onBatteryClick,
            onAllowlistClick = onAllowlistClick,
            onSettingsClick = onSettingsClick
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}
