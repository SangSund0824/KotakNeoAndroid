package com.example.core.network.di

import com.example.core.network.utils.NetworkErrorHandler
import com.example.core.network.utils.NetworkRetryError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultNetworkErrorHandler @Inject constructor() : NetworkErrorHandler {
    override suspend fun handleError(error: NetworkRetryError): Boolean {
        // For now, don't handle errors - just let them propagate
        return false
    }

    override suspend fun errorHandled() {
        // No-op for now
    }
}
