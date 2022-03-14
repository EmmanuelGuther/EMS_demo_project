package com.emmanuelguther.presentation.model

import com.emmanuelguther.presentation.utils.isPositive
import com.emmanuelguther.presentation.utils.maxDecimals
import kotlin.math.abs

typealias DaysEnergyHistoric = List<DayEnergyHistoric>

data class DayEnergyHistoric(val year: Int, val month: Int, val day: Int, val hourEnergyHistoric: List<HourEnergyHistoric>) {
    val readableDate = "$year/$month/$day"
}

data class HourEnergyHistoric(
    val timestampYMDH: Long,
    val hour: Int,
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double
) {
    val dischargedFromQuasar = if (quasarsActivePower.isPositive() == false) abs(quasarsActivePower) else 0.0
    val chargedFromQuasar = if (quasarsActivePower.isPositive() == true) abs(quasarsActivePower) else 0.0
    val solarPercent = ((pvActivePower / buildingActivePower) * 100).maxDecimals(2)
    val gridPercent = ((gridActivePower / buildingActivePower) * 100).maxDecimals(2)
    val quasarPercent = ((quasarsActivePower / buildingActivePower) * 100).maxDecimals(2)
}
