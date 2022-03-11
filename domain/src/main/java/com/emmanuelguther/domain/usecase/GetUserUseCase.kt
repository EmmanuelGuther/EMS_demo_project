package com.emmanuelguther.domain.usecase

import com.emmanuelguther.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): String = repository.getUser()
}