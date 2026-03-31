package com.example.core.network.datasource

import com.example.core.network.model.portfolio.HoldingsResponse

interface PortfolioDataSource {
    suspend fun getHoldings(): HoldingsResponse
}
