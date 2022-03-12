package com.emmanuelguther.domain.repository

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.domain.entity.HistoricEntity
import kotlinx.coroutines.flow.Flow

interface EnergyRepository {
    suspend fun getHistoric(): Flow<ResultData<HistoricEntity>>
}