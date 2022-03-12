package com.emmanuelguther.data.repository

import com.emmanuelguther.data.datasource.local.UserLocalDataSource
import com.emmanuelguther.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepositoryImpl @Inject constructor(private val userLocalDataSource: UserLocalDataSource) : UserRepository {
    override fun getUser(): String = userLocalDataSource.getUser()
}