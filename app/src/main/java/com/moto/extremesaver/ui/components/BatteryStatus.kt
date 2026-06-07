package com.moto.extremesaver.ui.components

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.extremesaver.ui.theme.ElectricBlue
import com.moto.extremesaver.ui.theme.LimeGreen
import kotlinx.coroutines.delay

@Composable
fun BatteryStatus(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var batteryLevel by remember { mutableIntStateOf(100) }
    var isCharging by remember { mutableStateOf(false) }

    // Periodic refresh every 30s (event-driven via BatteryChangedReceiver handles fast updates;
    // this is a fallback to keep the composable UI in sync)
    LaunchedEffect(Unit) {
        while (true) {
            val batteryStatus: Intent? = context.registerReceiver(
                null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            batteryStatus?.let {
                val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val status = it.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                if (level >= 0 && scale > 0) {
                    batteryLevel = (level * 100) / scale
                }
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL
            }
            delay(30_000L)
        }
    }

    val color = if (batteryLevel > 20) LimeGreen else ElectricBlue
    val estimatedHours = (batteryLevel * 0.5).toInt()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = "$batteryLevel%",
            color = color,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isCharging) "⚡ Charging" else "~$estimatedHours hrs remaining",
            color = color.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
