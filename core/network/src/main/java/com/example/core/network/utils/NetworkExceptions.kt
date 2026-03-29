package com.example.core.network.utils

sealed class NetworkExceptions(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

data class InvalidAuthTokenException(val code: Int, val errorMessage: String) : NetworkExceptions(errorMessage)
data class BadCredentialsException(val code: Int, val errorMessage: String) : NetworkExceptions(errorMessage)
data class TCVException(
    val errorCode: Int,
    val msg: String?,
    val retriable: Boolean = false,
    val transactionId: String? = null,
    val throwable: Throwable? = null
) : NetworkExceptions(msg, throwable)

object NoInternetException : NetworkExceptions("No internet connection")
object TimedOutException : NetworkExceptions("Request timed out")
object CanceledException : NetworkExceptions("Request canceled")
object NoBodyException : NetworkExceptions("Response body is null")
data class NoTokenException(override val message: String?) : NetworkExceptions(message)
data class UnknownNetworkException(val throwable: Throwable) : NetworkExceptions(throwable.message, throwable)
