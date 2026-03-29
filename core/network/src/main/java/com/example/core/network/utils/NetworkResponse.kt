package com.example.core.network.utils

sealed class NetworkResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: NetworkExceptions) : NetworkResponse<Nothing>()
}

fun <T : Any> NetworkResponse<T>.networkResult(): T {
    return when (this) {
        is NetworkResponse.Success -> data
        is NetworkResponse.Error -> throw exception
    }
}
