package com.moto.extremesaver.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.extremesaver.ui.theme.ElectricBlue
import com.moto.extremesaver.ui.theme.LimeGreen

@Composable
fun BatteryStatus(
    batteryLevel: Int,
    isCharging: Boolean,
    estimatedHours: Int,
    modifier: Modifier = Modifier
) {
    // ⚡ Bolt: Removed 30s polling fallback.
    // Impact: Eliminates unnecessary 30s recomposition loop and IntentFilter queries. UI now relies purely on the event-driven BatteryChangedReceiver via ViewModel state.
    val color = if (batteryLevel > 20) LimeGreen else ElectricBlue

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
