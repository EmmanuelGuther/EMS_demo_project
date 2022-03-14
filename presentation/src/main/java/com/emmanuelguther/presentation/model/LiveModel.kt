package com.emmanuelguther.presentation.model

import com.emmanuelguther.presentation.utils.maxDecimals

data class LiveModel(
    val _buildingDemand: Double? =null,
    val currentEnergy: Int? = null,
    val _gridPower: Double? = null,
    val _quasarsPower: Double? = null,
    val _solarPower: Double? = null,
    val _systemSoc: Double? = null,
    val totalEnergy: Int? = null
){

    val building = _buildingDemand?.maxDecimals(2)
    val grid: Double? = _gridPower?.maxDecimals(2)
    val quasars= _quasarsPower?.maxDecimals(2)
    val solar = _solarPower?.maxDecimals(2)
    val system= _systemSoc?.maxDecimals(2)
}