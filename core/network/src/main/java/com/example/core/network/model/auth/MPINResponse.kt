package com.example.core.network.model.auth

data class MPINResponse(
    val data: MPINData? = null
)

data class MPINData(
    val token: String? = null,
    val sid: String? = null,
    val baseUrl: String? = null,
    val kType: String? = null,
    val status: String? = null
)
