package com.moto.extremesaver.domain.repository

import com.moto.extremesaver.data.entity.AllowedAppEntity
import com.moto.extremesaver.data.entity.BatteryStatsEntity
import com.moto.extremesaver.data.entity.ModeStateEntity
import com.moto.extremesaver.data.entity.RestrictionStateEntity
import com.moto.extremesaver.data.entity.UsageEventEntity
import kotlinx.coroutines.flow.Flow

interface ModeRepository {
    fun observeModeState(): Flow<ModeStateEntity?>
    suspend fun getModeState(): ModeStateEntity?
    suspend fun saveModeState(state: ModeStateEntity)
}

interface AllowlistRepository {
    fun observeAllowedApps(): Flow<List<AllowedAppEntity>>
    suspend fun getAllowedPackageNames(): List<String>
    suspend fun addApp(app: AllowedAppEntity)
    suspend fun removeApp(packageName: String)
    suspend fun isAppAllowed(packageName: String): Boolean
}

interface BatteryRepository {
    fun observeLatestStat(): Flow<BatteryStatsEntity?>
    suspend fun recordStat(stat: BatteryStatsEntity)
    suspend fun getRecentStats(limit: Int = 24): List<BatteryStatsEntity>
    suspend fun pruneOldStats(olderThanMs: Long)
}

interface RestrictionRepository {
    fun observeAllRestrictions(): Flow<List<RestrictionStateEntity>>
    suspend fun setRestriction(restriction: RestrictionStateEntity)
    suspend fun getRestriction(feature: String): RestrictionStateEntity?
}

interface UsageEventRepository {
    suspend fun logEvent(event: UsageEventEntity)
    suspend fun getRecentEvents(limit: Int = 50): List<UsageEventEntity>
    suspend fun pruneOldEvents(olderThanMs: Long)
    suspend fun countEventsSince(type: String, sinceMs: Long): Int
}
