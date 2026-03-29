package com.example.core.network.utils

import com.example.core.network.utils.NetworkExceptions

data class NetworkRetryError(
    val networkException: NetworkExceptions,
    val retryAction: () -> Unit
)

interface NetworkErrorHandler {
    suspend fun handleError(error: NetworkRetryError): Boolean
    suspend fun errorHandled()
}
