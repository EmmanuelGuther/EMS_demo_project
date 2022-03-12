package com.emmanuelguther.data.repository

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.data.network.datasource.EnergyRemoteDataSource
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.repository.EnergyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class EnergyRepositoryImpl @Inject constructor(private val energyRemoteDataSource: EnergyRemoteDataSource) : EnergyRepository {
    override suspend fun getHistoric(): Flow<ResultData<HistoricEntity>> = callbackFlow {
        launch(Dispatchers.IO) {
            trySend(ResultData.Loading())
            trySend(energyRemoteDataSource.getHistoric())
        }
        awaitClose { close() }
    }
}