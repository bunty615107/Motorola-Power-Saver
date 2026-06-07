package com.moto.extremesaver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "allowed_apps")
data class AllowedAppEntity(
    @PrimaryKey val packageName: String,
    val appName: String,
    val isSystemApp: Boolean = false,
    val addedAt: Long = System.currentTimeMillis()
)
