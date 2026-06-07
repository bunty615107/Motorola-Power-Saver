package com.moto.extremesaver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mode_state")
data class ModeStateEntity(
    @PrimaryKey val id: Int = 1,
    val currentMode: String = "STANDARD", // STANDARD, TRANSITIONING, EXTREME_SAVER
    val activatedAt: Long = 0L,
    val deactivatedAt: Long = 0L,
    val activationCount: Int = 0
)
