package com.emmanuelguther.di

import com.emmanuelguther.data.local.UserLocalDataSource
import com.emmanuelguther.data.network.datasource.EnergyRemoteDataSource
import com.emmanuelguther.data.network.service.EnergyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourcesModule {

    @Singleton
    @Provides
    fun providesUserLocalDataSource() = UserLocalDataSource()

    @Singleton
    @Provides
    fun providesEnergyRemoteDataSource(energyService: EnergyService) = EnergyRemoteDataSource(energyService)


}