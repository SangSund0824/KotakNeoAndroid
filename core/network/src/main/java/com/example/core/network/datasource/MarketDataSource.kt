package com.example.core.network.datasource

import com.example.core.network.model.market.ScripMasterResponse

interface MarketDataSource {
    suspend fun getScripMaster(segment: String = "nse_cm"): ScripMasterResponse
}
