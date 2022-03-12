package com.emmanuelguther.data.repository

import com.emmanuelguther.data.datasource.network.EnergyDataRemoteDataSource
import com.emmanuelguther.domain.repository.EnergyDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class EnergyDataRepositoryImpl @Inject constructor(private val energyDataRemoteDataSource: EnergyDataRemoteDataSource) :
    EnergyDataRepository {
    override fun getHistoric(): String = energyDataRemoteDataSource.getHistoric()
}