package com.moto.extremesaver.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moto.extremesaver.data.dao.*
import com.moto.extremesaver.data.entity.*

@Database(
    entities = [
        ModeStateEntity::class,
        AllowedAppEntity::class,
        BatteryStatsEntity::class,
        RestrictionStateEntity::class,
        UsageEventEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExtremeSaverDatabase : RoomDatabase() {
    abstract fun modeStateDao(): ModeStateDao
    abstract fun allowedAppDao(): AllowedAppDao
    abstract fun batteryStatsDao(): BatteryStatsDao
    abstract fun restrictionStateDao(): RestrictionStateDao
    abstract fun usageEventDao(): UsageEventDao
}
