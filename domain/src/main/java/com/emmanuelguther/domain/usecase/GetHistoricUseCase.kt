package com.emmanuelguther.domain.usecase

import com.emmanuelguther.domain.repository.EnergyDataRepository
import javax.inject.Inject

class GetHistoricUseCase @Inject constructor(private val repository: EnergyDataRepository) {
    operator fun invoke(): String = repository.getHistoric()
}