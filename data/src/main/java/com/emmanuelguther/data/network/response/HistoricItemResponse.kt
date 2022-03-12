package com.emmanuelguther.data.network.response


import com.google.gson.annotations.SerializedName

data class HistoricItemResponse(
    @SerializedName("building_active_power")
    val buildingActivePower: Double,
    @SerializedName("grid_active_power")
    val gridActivePower: Double,
    @SerializedName("pv_active_power")
    val pvActivePower: Double,
    @SerializedName("quasars_active_power")
    val quasarsActivePower: Double,
    @SerializedName("timestamp")
    val timestamp: String
)