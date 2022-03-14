package com.emmanuelguther.presentation.model

typealias DaysEnergyHistoric = List<DayEnergyHistoric>

data class DayEnergyHistoric(val year: Int, val month: Int, val day: Int, val hourEnergyHistoric: List<HourEnergyHistoric>){
    val readableDate = "$year/$month/$day"
}

data class HourEnergyHistoric(
    val timestampYMDH: Long,
    val hour: Int,
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double
)
