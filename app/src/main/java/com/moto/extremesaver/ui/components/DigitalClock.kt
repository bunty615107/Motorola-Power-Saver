package com.moto.extremesaver.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DigitalClock(modifier: Modifier = Modifier) {
    var currentTimeMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Cache formatters to avoid re-allocation on every recomposition
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTimeMillis = System.currentTimeMillis()
            delay(1_000L)
        }
    }

    val date = remember(currentTimeMillis) { Date(currentTimeMillis) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = timeFormat.format(date),
            color = Color.White,
            fontSize = 80.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp
        )
        Text(
            text = dateFormat.format(date),
            color = Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp
        )
    }
}
