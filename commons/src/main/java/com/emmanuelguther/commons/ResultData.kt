package com.emmanuelguther.commons

sealed class ResultData<out T> {
    class Loading<out T> :ResultData<T>()

    data class Success<out T>(
        val data: T
    ): ResultData<T>()

    data class Failure<out T>(
        val errorMessage: String?=null, val exception: Exception?= null
    ): ResultData<T>()
}

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `Flow<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */
