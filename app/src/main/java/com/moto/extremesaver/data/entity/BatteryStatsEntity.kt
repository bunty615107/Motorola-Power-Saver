package com.moto.extremesaver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_stats")
data class BatteryStatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val batteryLevel: Int,
    val isCharging: Boolean,
    val temperature: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val modeActive: Boolean = false
)
