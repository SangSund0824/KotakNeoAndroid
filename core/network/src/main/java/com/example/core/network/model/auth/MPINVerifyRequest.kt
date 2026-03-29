package com.example.core.network.model.auth

data class MPINVerifyRequest(
    val mpin: String,
    val sid: String,
    val token: String
)
