package com.moto.extremesaver.data.dao

import androidx.room.*
import com.moto.extremesaver.data.entity.UsageEventEntity

@Dao
interface UsageEventDao {
    @Insert
    suspend fun insertEvent(event: UsageEventEntity)

    @Query("SELECT * FROM usage_events ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentEvents(limit: Int = 50): List<UsageEventEntity>

    @Query("DELETE FROM usage_events WHERE timestamp < :before")
    suspend fun pruneOldEvents(before: Long)

    @Query("SELECT COUNT(*) FROM usage_events WHERE eventType = :type AND timestamp > :since")
    suspend fun countEventsSince(type: String, since: Long): Int
}
