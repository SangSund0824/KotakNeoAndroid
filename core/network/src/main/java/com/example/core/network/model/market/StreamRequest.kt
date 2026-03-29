package com.example.core.network.model.market

data class StreamRequest(
    val base_url: String,
    val token: String,
    val sid: String,
    val symbols: List<String>
)

data class StreamResponse(
    val status: String
)
