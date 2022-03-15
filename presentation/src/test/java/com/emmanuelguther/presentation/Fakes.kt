package com.emmanuelguther.presentation

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.entity.HistoricItemEntity
import com.emmanuelguther.domain.entity.LiveEntity
import com.emmanuelguther.domain.repository.EnergyRepository
import com.emmanuelguther.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Fakes {
    companion object {

        val historicData = HistoricItemEntity(
            buildingActivePower = 50.5,
            gridActivePower = 4.5,
            pvActivePower = 0.0,
            quasarsActivePower = 6.5,
            timestamp = "2021-09-26T22:01:00+00:00"
        )

        val liveData = LiveEntity(
            buildingDemand = 100.0,
            currentEnergy = 60,
            gridPower = 4.5,
            quasarsPower = 6.5,
            solarPower = 0.0,
            systemSoc = 0.0,
            totalEnergy = 71
        )

        const val error = "error"

        sealed class ResultType {
            object Success : ResultType()
            object Failure : ResultType()
        }
    }

    class FakeEnergyRepository(private val resultType: ResultType) : EnergyRepository {
        override suspend fun getHistoric(): Flow<ResultData<HistoricEntity>> = when(resultType){
            ResultType.Failure -> flowOf(ResultData.Failure(error))
            ResultType.Success -> flowOf(ResultData.Success(listOf(historicData)))
                }

        override suspend fun getLive(): Flow<ResultData<LiveEntity>> = when(resultType){
            ResultType.Failure -> flowOf(ResultData.Failure(error))
            ResultType.Success -> flowOf(ResultData.Success(liveData))
        }


    }


}





