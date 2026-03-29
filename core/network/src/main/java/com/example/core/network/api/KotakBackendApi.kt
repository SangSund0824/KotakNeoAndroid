package com.example.core.network.api

import com.example.core.network.model.auth.*
import com.example.core.network.model.order.OrderRequest
import com.example.core.network.model.market.*
import com.example.core.network.model.portfolio.PositionsResponse
import com.example.core.network.utils.NetworkResponse
import retrofit2.http.*

interface KotakBackendApi {

    @GET("/health")
    suspend fun healthCheck(): NetworkResponse<Map<String, String>>

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<LoginResponse>

    @POST("/auth/mpin")
    suspend fun verifyMPIN(@Body request: MPINVerifyRequest): NetworkResponse<MPINResponse>

    @POST
    suspend fun generateOTP(
        @Url url: String,
        @Body request: GenerateOTPRequest
    ): NetworkResponse<GenerateOTPResponse>

    @POST
    suspend fun submitOTP(
        @Url url: String,
        @Body request: SubmitOTPRequest
    ): NetworkResponse<SubmitOTPResponse>

    @GET
    suspend fun getFilePaths(
        @Url url: String,
        @Header("Authorization") authHeader: String
    ): NetworkResponse<Map<String, Any>>

    @GET("/scripmaster")
    suspend fun getScripMaster(
        @Query("segment") segment: String = "nse_cm"
    ): NetworkResponse<ScripMasterResponse>

    @POST("/stream")
    suspend fun startStream(
        @Query("base_url") baseUrl: String,
        @Query("token") token: String,
        @Query("sid") sid: String,
        @Body symbols: List<String>
    ): NetworkResponse<StreamResponse>

    @GET("/market/quotes/{symbol}")
    suspend fun getQuotes(
        @Path("symbol") symbol: String,
        @Query("token") token: String,
        @Query("sid") sid: String,
        @Query("base_url") baseUrl: String
    ): NetworkResponse<QuoteResponse>

    @POST("/trade/place-order")
    suspend fun placeOrder(@Body request: OrderRequest): NetworkResponse<Map<String, Any>>

    @GET("/portfolio/positions")
    suspend fun getPositions(): NetworkResponse<PositionsResponse>
}
