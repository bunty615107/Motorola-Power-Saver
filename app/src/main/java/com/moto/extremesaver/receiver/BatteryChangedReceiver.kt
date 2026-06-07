package com.moto.extremesaver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log

/**
 * Event-driven battery monitor. Registered dynamically (not in manifest)
 * to avoid waking the app unnecessarily. Only active while the app is running.
 */
class BatteryChangedReceiver(
    private val onBatteryChanged: (level: Int, isCharging: Boolean, temperature: Int) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BATTERY_CHANGED) return

        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)

        val batteryPct = if (level >= 0 && scale > 0) {
            (level * 100) / scale
        } else {
            -1
        }

        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        if (batteryPct >= 0) {
            Log.d("BatteryReceiver", "Battery: $batteryPct%, Charging: $isCharging, Temp: ${temperature / 10}°C")
            onBatteryChanged(batteryPct, isCharging, temperature / 10)
        }
    }

    companion object {
        fun createIntentFilter(): IntentFilter {
            return IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        }
    }
}
