package com.example.core.network.datasource

import com.example.core.network.model.auth.*

interface AuthNetworkDataSource {
    suspend fun login(mobileNumber: String, ucc: String, totp: String): LoginResponse
    suspend fun verifyMPIN(mpin: String, sid: String, token: String): MPINResponse
    suspend fun generateOTP(baseUrl: String, mobileNumber: String): GenerateOTPResponse
    suspend fun submitOTP(baseUrl: String, otp: String): SubmitOTPResponse
}
