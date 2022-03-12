package com.emmanuelguther.data.network.service

import com.emmanuelguther.data.network.ApiRoutes
import com.emmanuelguther.data.network.response.HistoricResponse
import retrofit2.http.GET

interface EnergyService {
    @GET(ApiRoutes.HISTORIC)
    suspend fun getHistoric(): HistoricResponse
}