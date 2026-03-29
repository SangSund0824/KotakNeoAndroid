package com.example.core.network.client

import com.example.core.network.api.KotakBackendApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://16.171.55.251"

    val instance: KotakBackendApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(KotakBackendApi::class.java)
    }
}
