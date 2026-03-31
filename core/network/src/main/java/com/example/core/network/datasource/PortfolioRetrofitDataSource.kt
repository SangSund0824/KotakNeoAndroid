package com.example.core.network.datasource

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.portfolio.HoldingsResponse
import com.example.core.network.utils.networkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRetrofitDataSource @Inject constructor(
    private val api: KotakBackendApi
) : PortfolioDataSource {
    override suspend fun getHoldings(): HoldingsResponse =
        api.getHoldings().networkResult()
}
