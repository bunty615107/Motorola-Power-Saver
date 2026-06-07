package com.moto.extremesaver.data.dao

import androidx.room.*
import com.moto.extremesaver.data.entity.AllowedAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AllowedAppDao {
    @Query("SELECT * FROM allowed_apps ORDER BY appName ASC")
    fun observeAllowedApps(): Flow<List<AllowedAppEntity>>

    @Query("SELECT packageName FROM allowed_apps")
    suspend fun getAllowedPackageNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: AllowedAppEntity)

    @Delete
    suspend fun removeApp(app: AllowedAppEntity)

    @Query("DELETE FROM allowed_apps WHERE packageName = :packageName")
    suspend fun removeByPackageName(packageName: String)

    @Query("SELECT EXISTS(SELECT 1 FROM allowed_apps WHERE packageName = :packageName)")
    suspend fun isAppAllowed(packageName: String): Boolean
}
