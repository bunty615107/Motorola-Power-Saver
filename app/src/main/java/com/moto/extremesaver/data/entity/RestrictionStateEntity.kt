package com.moto.extremesaver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restriction_state")
data class RestrictionStateEntity(
    @PrimaryKey val featureName: String, // wifi, bluetooth, location, sync, etc.
    val isRestricted: Boolean = false,
    val method: String = "", // SETTINGS_PANEL, DIRECT_API, USER_PROMPT
    val lastModified: Long = System.currentTimeMillis()
)
