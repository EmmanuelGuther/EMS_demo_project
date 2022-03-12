package com.emmanuelguther.data.network.response

import com.google.gson.annotations.SerializedName


/**
 *This class is just to have an EXAMPLE of how we would handle errors, the api we call does not have this specific error handling.
 */
data class ErrorResponse(
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("message") val message: String?
)
