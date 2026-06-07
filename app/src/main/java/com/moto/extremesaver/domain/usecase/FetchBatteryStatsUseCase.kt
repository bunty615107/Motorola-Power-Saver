package com.moto.extremesaver.domain.usecase

import com.moto.extremesaver.data.entity.BatteryStatsEntity
import com.moto.extremesaver.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class BatteryInfo(
    val level: Int,
    val isCharging: Boolean,
    val estimatedHoursRemaining: Int,
    val drainRatePerHour: Float
)

class FetchBatteryStatsUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository
) {
    fun observeLatest(): Flow<BatteryStatsEntity?> =
        batteryRepository.observeLatestStat()

    suspend fun record(level: Int, isCharging: Boolean, temperature: Int, modeActive: Boolean) {
        batteryRepository.recordStat(
            BatteryStatsEntity(
                batteryLevel = level,
                isCharging = isCharging,
                temperature = temperature,
                modeActive = modeActive
            )
        )
    }

    suspend fun calculateBatteryInfo(): BatteryInfo {
        val stats = batteryRepository.getRecentStats(12)
        val latest = stats.firstOrNull()

        if (stats.size < 2 || latest == null) {
            return BatteryInfo(
                level = latest?.batteryLevel ?: 100,
                isCharging = latest?.isCharging ?: false,
                estimatedHoursRemaining = (latest?.batteryLevel ?: 100) / 2,
                drainRatePerHour = 0f
            )
        }

        val oldest = stats.last()
        val levelDrop = oldest.batteryLevel - latest.batteryLevel
        val timeDiffHours = (latest.timestamp - oldest.timestamp) / 3_600_000f

        val drainRate = if (timeDiffHours > 0 && levelDrop > 0) {
            levelDrop / timeDiffHours
        } else {
            2f // Default assumption: 2% per hour
        }

        val estimatedHours = if (drainRate > 0) {
            (latest.batteryLevel / drainRate).toInt()
        } else {
            latest.batteryLevel / 2
        }

        return BatteryInfo(
            level = latest.batteryLevel,
            isCharging = latest.isCharging,
            estimatedHoursRemaining = estimatedHours,
            drainRatePerHour = drainRate
        )
    }
}
