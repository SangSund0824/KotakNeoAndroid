package com.example.core.network.datasource

import com.example.core.network.model.order.OrderRequest

interface OrderDataSource {
    suspend fun placeOrder(request: OrderRequest): Map<String, Any>
}
