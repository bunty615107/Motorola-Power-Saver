package com.moto.extremesaver.data.repository

import com.moto.extremesaver.data.dao.*
import com.moto.extremesaver.data.entity.*
import com.moto.extremesaver.domain.repository.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModeRepositoryImpl @Inject constructor(
    private val dao: ModeStateDao
) : ModeRepository {
    override fun observeModeState(): Flow<ModeStateEntity?> = dao.observeModeState()
    override suspend fun getModeState(): ModeStateEntity? = dao.getModeState()
    override suspend fun saveModeState(state: ModeStateEntity) = dao.upsertModeState(state)
}

@Singleton
class AllowlistRepositoryImpl @Inject constructor(
    private val dao: AllowedAppDao
) : AllowlistRepository {
    override fun observeAllowedApps(): Flow<List<AllowedAppEntity>> = dao.observeAllowedApps()
    override suspend fun getAllowedPackageNames(): List<String> = dao.getAllowedPackageNames()
    override suspend fun addApp(app: AllowedAppEntity) = dao.insertApp(app)
    override suspend fun removeApp(packageName: String) = dao.removeByPackageName(packageName)
    override suspend fun isAppAllowed(packageName: String): Boolean = dao.isAppAllowed(packageName)
}

@Singleton
class BatteryRepositoryImpl @Inject constructor(
    private val dao: BatteryStatsDao
) : BatteryRepository {
    override fun observeLatestStat(): Flow<BatteryStatsEntity?> = dao.observeLatestStat()
    override suspend fun recordStat(stat: BatteryStatsEntity) = dao.insertStat(stat)
    override suspend fun getRecentStats(limit: Int): List<BatteryStatsEntity> = dao.getRecentStats(limit)
    override suspend fun pruneOldStats(olderThanMs: Long) = dao.pruneOldStats(System.currentTimeMillis() - olderThanMs)
}

@Singleton
class RestrictionRepositoryImpl @Inject constructor(
    private val dao: RestrictionStateDao
) : RestrictionRepository {
    override fun observeAllRestrictions(): Flow<List<RestrictionStateEntity>> = dao.observeAllRestrictions()
    override suspend fun setRestriction(restriction: RestrictionStateEntity) = dao.upsertRestriction(restriction)
    override suspend fun getRestriction(feature: String): RestrictionStateEntity? = dao.getRestriction(feature)
}

@Singleton
class UsageEventRepositoryImpl @Inject constructor(
    private val dao: UsageEventDao
) : UsageEventRepository {
    override suspend fun logEvent(event: UsageEventEntity) = dao.insertEvent(event)
    override suspend fun getRecentEvents(limit: Int): List<UsageEventEntity> = dao.getRecentEvents(limit)
    override suspend fun pruneOldEvents(olderThanMs: Long) = dao.pruneOldEvents(System.currentTimeMillis() - olderThanMs)
    override suspend fun countEventsSince(type: String, sinceMs: Long): Int = dao.countEventsSince(type, sinceMs)
}
