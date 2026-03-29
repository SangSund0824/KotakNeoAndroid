package com.example.core.network.model.order

data class OrderRequest(
    val base_url: String,
    val trading_token: String,
    val trading_sid: String,
    val trading_symbol: String,
    val quantity: Int,
    val order_type: String,
    val price: Double,
    val transaction_type: String
)
