package com.emmanuelguther.domain.error

/**
 * Thrown to indicate that there was an error that require special
 * handling while getting data from the remote data source.
 *
 * @property error the error that requires special handling.
 * @property message the detail message string.
 *
 */
class SpecialHandlingException(val error: Error, override val message: String? = null) : Throwable(message) {

    enum class Error(val code: String? = null) {
        UNKNOWN,
        INVALID_CREDENTIALS("0100")
    }

    companion object {
        fun parse(errorCode: String?, message: String?): SpecialHandlingException? =
            Error.values().firstOrNull { it.code == errorCode }?.let { SpecialHandlingException(it, message) }
    }
}
