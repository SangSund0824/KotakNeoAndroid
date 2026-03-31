package com.example.core.network.datasource

import com.example.core.network.model.portfolio.PositionsResponse

interface PositionsDataSource {
    suspend fun getPositions(): PositionsResponse
}
