package com.moto.extremesaver.di

import android.content.Context
import androidx.room.Room
import com.moto.extremesaver.data.ExtremeSaverDatabase
import com.moto.extremesaver.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExtremeSaverDatabase {
        return Room.databaseBuilder(
            context,
            ExtremeSaverDatabase::class.java,
            "extreme_saver_db"
        ).build()
    }

    @Provides fun provideModeStateDao(db: ExtremeSaverDatabase): ModeStateDao = db.modeStateDao()
    @Provides fun provideAllowedAppDao(db: ExtremeSaverDatabase): AllowedAppDao = db.allowedAppDao()
    @Provides fun provideBatteryStatsDao(db: ExtremeSaverDatabase): BatteryStatsDao = db.batteryStatsDao()
    @Provides fun provideRestrictionStateDao(db: ExtremeSaverDatabase): RestrictionStateDao = db.restrictionStateDao()
    @Provides fun provideUsageEventDao(db: ExtremeSaverDatabase): UsageEventDao = db.usageEventDao()
}
