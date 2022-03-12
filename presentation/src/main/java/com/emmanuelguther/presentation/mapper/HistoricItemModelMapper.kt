package com.emmanuelguther.presentation.mapper

import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.presentation.model.HistoricItemModel
import com.emmanuelguther.presentation.model.HistoricModel

fun HistoricEntity.domainToPresentation(): HistoricModel = this.map {
    HistoricItemModel(
        buildingActivePower = it.buildingActivePower,
        gridActivePower = it.gridActivePower,
        pvActivePower = it.pvActivePower,
        quasarsActivePower = it.quasarsActivePower,
        timestamp = it.timestamp
    )
}