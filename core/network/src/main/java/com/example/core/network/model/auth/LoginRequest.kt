package com.example.core.network.model.auth

data class LoginRequest(
    val mobileNumber: String,
    val ucc: String,
    val totp: String
)
