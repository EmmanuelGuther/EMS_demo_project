package com.emmanuelguther.presentation.mapper

import com.emmanuelguther.domain.entity.LiveEntity
import com.emmanuelguther.presentation.model.LiveModel

fun LiveEntity.domainToPresentation() = LiveModel(
    _buildingDemand = buildingDemand,
    currentEnergy = currentEnergy,
    _gridPower = gridPower,
    _quasarsPower = quasarsPower,
    _solarPower = solarPower,
    _systemSoc = systemSoc,
    totalEnergy = totalEnergy
)