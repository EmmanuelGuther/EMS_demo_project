package com.emmanuelguther.domain.entity

data class HistoricItemEntity(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double,
    val timestamp: String
)