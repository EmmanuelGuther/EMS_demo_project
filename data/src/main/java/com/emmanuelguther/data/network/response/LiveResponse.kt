package com.emmanuelguther.data.network.response


import com.google.gson.annotations.SerializedName

data class LiveResponse(
    @SerializedName("building_demand")
    val buildingDemand: Double = 0.0,
    @SerializedName("current_energy")
    val currentEnergy: Int = 0,
    @SerializedName("grid_power")
    val gridPower: Double = 0.0,
    @SerializedName("quasars_power")
    val quasarsPower: Double = 0.0,
    @SerializedName("solar_power")
    val solarPower: Double = 0.0,
    @SerializedName("system_soc")
    val systemSoc: Double = 0.0,
    @SerializedName("total_energy")
    val totalEnergy: Int = 0
)