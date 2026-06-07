package com.moto.extremesaver.data.dao

import androidx.room.*
import com.moto.extremesaver.data.entity.ModeStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModeStateDao {
    @Query("SELECT * FROM mode_state WHERE id = 1")
    fun observeModeState(): Flow<ModeStateEntity?>

    @Query("SELECT * FROM mode_state WHERE id = 1")
    suspend fun getModeState(): ModeStateEntity?

    @Upsert
    suspend fun upsertModeState(state: ModeStateEntity)
}
