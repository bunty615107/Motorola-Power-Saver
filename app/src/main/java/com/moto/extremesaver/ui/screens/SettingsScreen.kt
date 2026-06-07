package com.moto.extremesaver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onAccessibilityClick: () -> Unit,
    onNotificationAccessClick: () -> Unit,
    onWriteSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
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
                text = "Settings",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Permissions Section
        SectionHeader("Permissions")
        Spacer(modifier = Modifier.height(12.dp))

        SettingsButton(
            title = "Accessibility Service",
            subtitle = "Required to block unauthorized apps",
            onClick = onAccessibilityClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsButton(
            title = "Notification Access",
            subtitle = "Required to suppress non-essential notifications",
            onClick = onNotificationAccessClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsButton(
            title = "Modify System Settings",
            subtitle = "Required for brightness and timeout control",
            onClick = onWriteSettingsClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display Section
        SectionHeader("Display")
        Spacer(modifier = Modifier.height(12.dp))

        var forceLowBrightness by remember { mutableStateOf(true) }
        SettingsToggle(
            title = "Force Low Brightness",
            subtitle = "Lock brightness at 20% to save power",
            checked = forceLowBrightness,
            onCheckedChange = { forceLowBrightness = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        var force60Hz by remember { mutableStateOf(true) }
        SettingsToggle(
            title = "Force 60Hz Refresh Rate",
            subtitle = "Reduce from 144Hz to save significant battery",
            checked = force60Hz,
            onCheckedChange = { force60Hz = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Connectivity Section
        SectionHeader("Connectivity")
        Spacer(modifier = Modifier.height(12.dp))

        var disableSync by remember { mutableStateOf(true) }
        SettingsToggle(
            title = "Disable Auto-Sync",
            subtitle = "Stop all background account synchronization",
            checked = disableSync,
            onCheckedChange = { disableSync = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // About Section
        SectionHeader("About")
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkGray)
                .padding(20.dp)
        ) {
            Column {
                Text("Extreme Saver Mode", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Version 1.0.0", color = Color.Gray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Motorola Edge 50 Pro • Android 16", color = Color.Gray, fontSize = 13.sp)
                Text("No root • No cloud • Offline-first", color = LimeGreen.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        color = ElectricBlue,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

@Composable
private fun SettingsButton(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkGray)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Text("→", color = ElectricBlue, fontSize = 20.sp)
    }
}

@Composable
private fun SettingsToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkGray)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LimeGreen,
                checkedTrackColor = LimeGreen.copy(alpha = 0.3f),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color(0xFF333333)
            )
        )
    }
}
