package com.moto.extremesaver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usage_events")
data class UsageEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventType: String, // APP_BLOCKED, MODE_ACTIVATED, MODE_DEACTIVATED, SETTING_CHANGED
    val packageName: String = "",
    val details: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
