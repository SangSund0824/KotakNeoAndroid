package com.example.core.network.datasource

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.order.OrderRequest
import com.example.core.network.utils.networkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRetrofitDataSource @Inject constructor(
    private val api: KotakBackendApi
) : OrderDataSource {
    override suspend fun placeOrder(request: OrderRequest): Map<String, Any> =
        api.placeOrder(request).networkResult()
}
