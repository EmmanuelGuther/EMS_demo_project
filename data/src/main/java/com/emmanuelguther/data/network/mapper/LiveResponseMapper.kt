package com.emmanuelguther.data.network.mapper

import com.emmanuelguther.data.network.response.LiveResponse
import com.emmanuelguther.domain.entity.LiveEntity

fun LiveResponse.dataToDomain() = LiveEntity(
    buildingDemand = buildingDemand,
    currentEnergy = currentEnergy,
    gridPower = gridPower,
    quasarsPower = quasarsPower,
    solarPower = solarPower,
    systemSoc = systemSoc,
    totalEnergy = totalEnergy
)
