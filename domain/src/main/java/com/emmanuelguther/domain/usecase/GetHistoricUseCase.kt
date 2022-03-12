package com.emmanuelguther.domain.usecase

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.domain.entity.HistoricEntity
import com.emmanuelguther.domain.repository.EnergyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoricUseCase @Inject constructor(private val repository: EnergyRepository) {
    suspend operator fun invoke(): Flow<ResultData<HistoricEntity>> = repository.getHistoric()
}