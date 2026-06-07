package com.moto.extremesaver.domain.usecase

import com.moto.extremesaver.data.entity.AllowedAppEntity
import com.moto.extremesaver.domain.repository.AllowlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAllowlistUseCase @Inject constructor(
    private val allowlistRepository: AllowlistRepository
) {
    fun observeAllowedApps(): Flow<List<AllowedAppEntity>> =
        allowlistRepository.observeAllowedApps()

    suspend fun addApp(packageName: String, appName: String, isSystem: Boolean = false) {
        allowlistRepository.addApp(
            AllowedAppEntity(
                packageName = packageName,
                appName = appName,
                isSystemApp = isSystem
            )
        )
    }

    suspend fun removeApp(packageName: String) {
        allowlistRepository.removeApp(packageName)
    }

    suspend fun isAllowed(packageName: String): Boolean =
        allowlistRepository.isAppAllowed(packageName)
}
