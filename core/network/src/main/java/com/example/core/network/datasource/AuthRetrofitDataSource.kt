package com.example.core.network.datasource

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.auth.*
import com.example.core.network.utils.networkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRetrofitDataSource @Inject constructor(
    private val api: KotakBackendApi,
) : AuthNetworkDataSource {
    
    override suspend fun login(mobileNumber: String, ucc: String, totp: String): LoginResponse =
        api.login(LoginRequest(mobileNumber, ucc, totp)).networkResult()

    override suspend fun verifyMPIN(mpin: String, sid: String, token: String): MPINResponse =
        api.verifyMPIN(MPINVerifyRequest(mpin, sid, token)).networkResult()
    
    override suspend fun generateOTP(baseUrl: String, mobileNumber: String): GenerateOTPResponse {
        val url = "$baseUrl/login-service/generateOTP"
        return api.generateOTP(url, GenerateOTPRequest(mobileNumber)).networkResult()
    }
    
    override suspend fun submitOTP(baseUrl: String, otp: String): SubmitOTPResponse {
        val url = "$baseUrl/login-service/session/2fa"
        return api.submitOTP(url, SubmitOTPRequest(otp)).networkResult()
    }
}
