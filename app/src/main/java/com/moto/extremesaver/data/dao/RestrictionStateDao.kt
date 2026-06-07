package com.moto.extremesaver.data.dao

import androidx.room.*
import com.moto.extremesaver.data.entity.RestrictionStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestrictionStateDao {
    @Query("SELECT * FROM restriction_state")
    fun observeAllRestrictions(): Flow<List<RestrictionStateEntity>>

    @Upsert
    suspend fun upsertRestriction(restriction: RestrictionStateEntity)

    @Query("SELECT * FROM restriction_state WHERE featureName = :feature")
    suspend fun getRestriction(feature: String): RestrictionStateEntity?
}
