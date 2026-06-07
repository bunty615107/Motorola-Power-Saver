package com.moto.extremesaver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.extremesaver.ui.theme.DarkGray
import com.moto.extremesaver.ui.theme.ElectricBlue
import com.moto.extremesaver.ui.theme.LimeGreen
import com.moto.extremesaver.ui.viewmodel.BatteryDashboardViewModel

@Composable
fun BatteryDashboardScreen(
    viewModel: BatteryDashboardViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val info = uiState.currentInfo
    val batteryColor = if (info.level > 20) LimeGreen else ElectricBlue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "Battery Dashboard",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Large battery percentage
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${info.level}%",
                color = batteryColor,
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-2).sp
            )
        }

        // Charging status
        Text(
            text = if (info.isCharging) "⚡ Charging" else "On Battery",
            color = if (info.isCharging) LimeGreen else Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Stats grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Est. Remaining",
                value = "${info.estimatedHoursRemaining}h",
                color = batteryColor
            )
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Drain Rate",
                value = "%.1f%%/hr".format(info.drainRatePerHour),
                color = if (info.drainRatePerHour > 5) Color(0xFFFF4444) else ElectricBlue
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Mode",
                value = "Extreme",
                color = LimeGreen
            )
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Sync",
                value = "Disabled",
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Efficiency tip
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(DarkGray, Color(0xFF0D1B0D))
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "💡 Efficiency Tip",
                    color = LimeGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Lower screen brightness further to maximize battery life. Each 10% reduction adds ~30 minutes.",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: Color
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGray)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = color,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
