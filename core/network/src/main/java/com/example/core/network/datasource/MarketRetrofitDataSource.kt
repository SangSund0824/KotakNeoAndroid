package com.example.core.network.datasource

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.market.ScripMasterResponse
import com.example.core.network.utils.networkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRetrofitDataSource @Inject constructor(
    private val api: KotakBackendApi
) : MarketDataSource {
    override suspend fun getScripMaster(segment: String): ScripMasterResponse =
        api.getScripMaster(segment).networkResult()
}
