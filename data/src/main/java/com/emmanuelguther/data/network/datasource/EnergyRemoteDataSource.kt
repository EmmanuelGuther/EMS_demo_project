package com.emmanuelguther.data.network.datasource

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.data.network.mapper.dataToDomain
import com.emmanuelguther.data.network.response.LiveResponse
import com.emmanuelguther.data.network.util.runCatchingHandler
import com.emmanuelguther.data.network.service.EnergyService
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.entity.LiveEntity
import javax.inject.Inject

class EnergyRemoteDataSource @Inject constructor(private val service: EnergyService) {
    suspend fun getHistoric(): ResultData<HistoricEntity> = runCatchingHandler {
        service.getHistoric().dataToDomain()
    }

    suspend fun getLive(): ResultData<LiveEntity> = runCatchingHandler {
        service.getLive().dataToDomain()
    }
}