package com.emmanuelguther.di

import com.emmanuelguther.data.network.ApiRoutes
import com.emmanuelguther.data.network.service.EnergyService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply { addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY)) }
            .callTimeout(TIMEOUT_CALL, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
            .build()

    @Provides
    @Singleton
    fun provideEnergyService(converterFactory: Converter.Factory, client: OkHttpClient): EnergyService =
        Retrofit.Builder()
            .baseUrl(ApiRoutes.API_SERVICE_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(EnergyService::class.java)

    companion object {
        private const val TIMEOUT_CALL: Long = 5000
        private const val TIMEOUT_CONNECT: Long = 5000
    }
}
