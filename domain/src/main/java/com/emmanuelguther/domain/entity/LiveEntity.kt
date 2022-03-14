package com.emmanuelguther.domain.entity

//These values are null because they are supposed to be 'live' and I wanted to give it a margin of error and control it in the view, if it comes to null, I don't render the data.
data class LiveEntity(
    val buildingDemand: Double? = null,
    val currentEnergy: Int? = null,
    val gridPower: Double? = null,
    val quasarsPower: Double? = null,
    val solarPower: Double? = null,
    val systemSoc: Double? = null,
    val totalEnergy: Int? = null
)