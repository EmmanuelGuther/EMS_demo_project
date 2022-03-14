package com.emmanuelguther.domain.repository

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.entity.LiveEntity
import kotlinx.coroutines.flow.Flow

interface EnergyRepository {
    suspend fun getHistoric(): Flow<ResultData<HistoricEntity>>
    suspend fun getLive(): Flow<ResultData<LiveEntity>>
}