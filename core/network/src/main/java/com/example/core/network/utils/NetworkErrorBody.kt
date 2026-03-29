package com.example.core.network.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkErrorBody(
    @SerialName("error") val error: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("retriable") val retriable: Boolean? = null,
    @SerialName("transactionId") val transactionId: String? = null
) {
    fun errorMsg(): String? = error ?: message
}
