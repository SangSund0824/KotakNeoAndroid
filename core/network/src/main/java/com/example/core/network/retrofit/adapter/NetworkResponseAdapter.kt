package com.example.core.network.retrofit.adapter

import com.example.core.network.utils.NetworkErrorHandler
import com.example.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<R : Any>(
    private val successType: Type,
    private val scope: CoroutineScope,
    private val networkErrorHandler: NetworkErrorHandler,
    private val json: Json,
) : CallAdapter<R, Call<NetworkResponse<R>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): Call<NetworkResponse<R>> {
        return NetworkResponseCall(call, scope, networkErrorHandler, json)
    }
}
