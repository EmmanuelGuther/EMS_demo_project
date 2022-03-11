package com.emmanuelguther.di

import com.emmanuelguther.data.datasource.UserLocalDataSource
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


}