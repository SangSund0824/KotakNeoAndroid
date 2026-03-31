package com.example.core.network.model.portfolio

data class HoldingsResponse(
    val data: List<Holding>? = null
)

data class Holding(
    val displaySymbol: String? = null,
    val symbol: String? = null,
    val averagePrice: Double? = null,
    val quantity: Int? = null,
    val exchangeSegment: String? = null,
    val holdingCost: Double? = null,
    val mktValue: Double? = null,
    val closingPrice: Double? = null,
    val instrumentType: String? = null,
    val sellableQuantity: Int? = null
)
