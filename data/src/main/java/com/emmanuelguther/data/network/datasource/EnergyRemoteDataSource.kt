package com.emmanuelguther.data.network.datasource

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.data.network.mapper.dataToDomain
import com.emmanuelguther.data.network.util.runCatchingHandler
import com.emmanuelguther.data.network.service.EnergyService
import com.emmanuelguther.domain.entity.HistoricEntity
import javax.inject.Inject

class EnergyRemoteDataSource @Inject constructor(private val service: EnergyService) {
    suspend fun getHistoric(): ResultData<HistoricEntity> = runCatchingHandler {
        service.getHistoric().dataToDomain()
    }
}