package com.emmanuelguther.data.network

import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.data.network.response.ErrorResponse
import com.emmanuelguther.domain.error.SpecialHandlingException
import com.google.gson.Gson
import retrofit2.HttpException

/**
 * Calls the specified function [block] and returns its encapsulated
 * result if invocation was successful, catching any [Throwable]
 * exception that was thrown from the [block] function execution,
 * identifying if it requires special handling and encapsulating it as
 * a failure.
 */
inline fun <R> runCatchingHandler(block: () -> R): ResultData<R> {
    return try {
        ResultData.Success(block())
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()
        try {
            val errorResponse = Gson().fromJson(errorBody?.charStream(), ErrorResponse::class.java)
            ResultData.Failure(exception = SpecialHandlingException.parse(errorResponse?.errorCode, e.message) ?: e)
        } catch (ignore: Exception) {
            ResultData.Failure(exception = SpecialHandlingException(SpecialHandlingException.Error.UNKNOWN))
        }
    } catch (expected: Throwable) {
        ResultData.Failure(expected.message, expected)
    }
}