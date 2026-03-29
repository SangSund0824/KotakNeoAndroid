package com.example.core.network.model.auth

data class GenerateOTPRequest(
    val mobileNumber: String
)

data class GenerateOTPResponse(
    val message: String,
    val status: String
)

data class SubmitOTPRequest(
    val OTP: String
)

data class SubmitOTPResponse(
    val edit_token: String,
    val status: String
)
