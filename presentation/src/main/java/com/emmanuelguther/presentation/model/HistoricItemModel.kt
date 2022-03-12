package com.emmanuelguther.presentation.model

data class HistoricItemModel(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double,
    val timestamp: String
)

typealias EnergyValue = Double