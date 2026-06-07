package com.moto.extremesaver.di

import com.moto.extremesaver.data.repository.*
import com.moto.extremesaver.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindModeRepository(impl: ModeRepositoryImpl): ModeRepository

    @Binds @Singleton
    abstract fun bindAllowlistRepository(impl: AllowlistRepositoryImpl): AllowlistRepository

    @Binds @Singleton
    abstract fun bindBatteryRepository(impl: BatteryRepositoryImpl): BatteryRepository

    @Binds @Singleton
    abstract fun bindRestrictionRepository(impl: RestrictionRepositoryImpl): RestrictionRepository

    @Binds @Singleton
    abstract fun bindUsageEventRepository(impl: UsageEventRepositoryImpl): UsageEventRepository
}
