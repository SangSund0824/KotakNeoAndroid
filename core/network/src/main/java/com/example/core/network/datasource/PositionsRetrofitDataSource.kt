package com.example.core.network.datasource

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.portfolio.PositionsResponse
import com.example.core.network.utils.networkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionsRetrofitDataSource @Inject constructor(
    private val api: KotakBackendApi
) : PositionsDataSource {
    override suspend fun getPositions(): PositionsResponse =
        api.getPositions().networkResult()
}
