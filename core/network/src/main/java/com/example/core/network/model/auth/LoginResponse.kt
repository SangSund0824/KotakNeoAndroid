package com.example.core.network.model.auth

data class LoginResponse(
    val data: LoginData? = null,
    val message: String? = null,
    val result: String? = null
)

data class LoginData(
    val token: String? = null,
    val sid: String? = null,
    val kType: String? = null,
    val status: String? = null,
    val dataCenter: String? = null,
    val greetingName: String? = null,
    val ucc: String? = null
)
