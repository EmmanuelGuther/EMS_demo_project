package com.emmanuelguther.data.network.mapper

import com.emmanuelguther.data.network.response.HistoricResponse
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.entity.HistoricItemEntity

fun HistoricResponse.dataToDomain(): HistoricEntity = this.map {
    HistoricItemEntity(
        buildingActivePower = it.buildingActivePower,
        gridActivePower = it.gridActivePower,
        pvActivePower = it.pvActivePower,
        quasarsActivePower = it.quasarsActivePower,
        timestamp = it.timestamp
    )
}