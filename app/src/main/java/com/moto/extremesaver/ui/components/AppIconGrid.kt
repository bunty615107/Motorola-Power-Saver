package com.moto.extremesaver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.extremesaver.ui.theme.DarkGray
import com.moto.extremesaver.ui.theme.ElectricBlue
import com.moto.extremesaver.ui.theme.LimeGreen

data class AppAction(
    val title: String,
    val icon: String,
    val color: Color = Color.White,
    val bgColor: Color = DarkGray,
    val onClick: () -> Unit
)

@Composable
fun AppIconGrid(
    modifier: Modifier = Modifier,
    onPhoneClick: () -> Unit,
    onSmsClick: () -> Unit,
    onExitClick: () -> Unit,
    onBatteryClick: (() -> Unit)? = null,
    onAllowlistClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null
) {
    val actions = mutableListOf(
        AppAction("Phone", "📞", onClick = onPhoneClick),
        AppAction("SMS", "💬", onClick = onSmsClick)
    )

    onBatteryClick?.let {
        actions.add(AppAction("Battery", "🔋", color = LimeGreen, onClick = it))
    }

    onAllowlistClick?.let {
        actions.add(AppAction("Apps", "📱", color = ElectricBlue, onClick = it))
    }

    onSettingsClick?.let {
        actions.add(AppAction("Settings", "⚙️", color = Color.Gray, onClick = it))
    }

    actions.add(
        AppAction("Exit", "✕", color = Color(0xFFFF4444), bgColor = Color(0xFF220000), onClick = onExitClick)
    )

    Column(modifier = modifier) {
        // First row: 3 items
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            actions.take(3).forEach { action ->
                AppIconButton(action = action)
            }
        }

        // Second row: remaining items
        if (actions.size > 3) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                actions.drop(3).forEach { action ->
                    AppIconButton(action = action)
                }
            }
        }
    }
}

@Composable
private fun AppIconButton(action: AppAction) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { action.onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(action.bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = action.icon,
                fontSize = 26.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = action.title,
            color = Color.LightGray,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
