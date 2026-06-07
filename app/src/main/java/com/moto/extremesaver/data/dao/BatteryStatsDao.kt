package com.moto.extremesaver.data.dao

import androidx.room.*
import com.moto.extremesaver.data.entity.BatteryStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BatteryStatsDao {
    @Insert
    suspend fun insertStat(stat: BatteryStatsEntity)

    @Query("SELECT * FROM battery_stats ORDER BY timestamp DESC LIMIT 1")
    fun observeLatestStat(): Flow<BatteryStatsEntity?>

    @Query("SELECT * FROM battery_stats ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentStats(limit: Int = 24): List<BatteryStatsEntity>

    @Query("DELETE FROM battery_stats WHERE timestamp < :before")
    suspend fun pruneOldStats(before: Long)
}
