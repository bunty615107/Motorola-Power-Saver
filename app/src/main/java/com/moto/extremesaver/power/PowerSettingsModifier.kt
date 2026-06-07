package com.moto.extremesaver.power

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log

/**
 * Aggressively enforces power-saving system settings.
 * Saves original values before overwriting so they can be accurately restored.
 */
class PowerSettingsModifier(private val context: Context) {

    companion object {
        private const val TAG = "PowerSettings"
        private const val PREFS_NAME = "power_settings_backup"
        private const val KEY_ORIGINAL_TIMEOUT = "original_timeout"
        private const val KEY_ORIGINAL_BRIGHTNESS_MODE = "original_brightness_mode"
        private const val KEY_ORIGINAL_BRIGHTNESS = "original_brightness"
        private const val KEY_ORIGINAL_SYNC = "original_sync"

        private const val EXTREME_TIMEOUT_MS = 15_000
        private const val EXTREME_BRIGHTNESS = 51 // ~20% of 255
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun enableExtremePowerSettings() {
        if (!Settings.System.canWrite(context)) {
            Log.e(TAG, "Missing WRITE_SETTINGS permission")
            return
        }

        val resolver = context.contentResolver

        try {
            // Save originals before overwriting
            saveOriginalSettings(resolver)

            // 1. Screen timeout → 15 seconds
            Settings.System.putInt(resolver, Settings.System.SCREEN_OFF_TIMEOUT, EXTREME_TIMEOUT_MS)

            // 2. Disable auto-brightness
            Settings.System.putInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )

            // 3. Lower brightness to ~20%
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, EXTREME_BRIGHTNESS)

            // 4. Disable auto-sync globally
            ContentResolver.setMasterSyncAutomatically(false)

            Log.d(TAG, "Extreme power settings applied.")
        } catch (e: Exception) {
            Log.e(TAG, "Error applying power settings: ${e.message}")
        }
    }

    fun restoreStandardPowerSettings() {
        if (!Settings.System.canWrite(context)) return

        val resolver = context.contentResolver

        try {
            val originalTimeout = prefs.getInt(KEY_ORIGINAL_TIMEOUT, 60_000)
            val originalBrightnessMode = prefs.getInt(
                KEY_ORIGINAL_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            )
            val originalSync = prefs.getBoolean(KEY_ORIGINAL_SYNC, true)

            Settings.System.putInt(resolver, Settings.System.SCREEN_OFF_TIMEOUT, originalTimeout)
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, originalBrightnessMode)
            ContentResolver.setMasterSyncAutomatically(originalSync)

            Log.d(TAG, "Standard power settings restored.")
        } catch (e: Exception) {
            Log.e(TAG, "Error restoring power settings: ${e.message}")
        }
    }

    private fun saveOriginalSettings(resolver: ContentResolver) {
        // Only save if we haven't already (avoid overwriting backup with extreme values)
        if (prefs.contains(KEY_ORIGINAL_TIMEOUT)) return

        val currentTimeout = Settings.System.getInt(resolver, Settings.System.SCREEN_OFF_TIMEOUT, 60_000)
        val currentBrightnessMode = Settings.System.getInt(
            resolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        )
        val currentSync = ContentResolver.getMasterSyncAutomatically()

        prefs.edit()
            .putInt(KEY_ORIGINAL_TIMEOUT, currentTimeout)
            .putInt(KEY_ORIGINAL_BRIGHTNESS_MODE, currentBrightnessMode)
            .putBoolean(KEY_ORIGINAL_SYNC, currentSync)
            .apply()

        Log.d(TAG, "Original settings saved: timeout=$currentTimeout, brightnessMode=$currentBrightnessMode, sync=$currentSync")
    }
}
