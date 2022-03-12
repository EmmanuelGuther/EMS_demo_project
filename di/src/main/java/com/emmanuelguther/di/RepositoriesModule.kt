package com.emmanuelguther.di

import com.emmanuelguther.data.local.UserLocalDataSource
import com.emmanuelguther.data.network.datasource.EnergyRemoteDataSource
import com.emmanuelguther.data.repository.EnergyRepositoryImpl
import com.emmanuelguther.data.repository.UserRepositoryImpl
import com.emmanuelguther.domain.repository.EnergyRepository
import com.emmanuelguther.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Singleton
    @Provides
    fun providesUserRepository(userLocalDataSource: UserLocalDataSource): UserRepository =
        UserRepositoryImpl(userLocalDataSource)

    @Singleton
    @Provides
    fun providesEnergyRepository(energyRemoteDataSource: EnergyRemoteDataSource): EnergyRepository =
        EnergyRepositoryImpl(energyRemoteDataSource)
}