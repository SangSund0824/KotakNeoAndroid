package com.example.core.data.repository

import com.example.core.data.db.StockDao
import com.example.core.data.db.StockEntity
import com.example.core.data.db.SyncMetadata
import com.example.core.network.datasource.MarketDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val stockDao: StockDao,
    private val marketDataSource: MarketDataSource
) {
    companion object {
        private const val STALE_THRESHOLD_MS = 24 * 60 * 60 * 1000L
    }

    suspend fun loadStocksIfNeeded() {
        val lastSync = stockDao.getLastSyncTime() ?: 0L
        val isStale = System.currentTimeMillis() - lastSync > STALE_THRESHOLD_MS
        val isEmpty = stockDao.count() == 0
        if (isEmpty || isStale) {
            refreshStocks()
        }
    }

    suspend fun refreshStocks() {
        val response = marketDataSource.getScripMaster("nse_cm")
        val stocks = response.stocks ?: emptyList()

        val entities = stocks.mapNotNull { row ->
            // Try common Kotak CSV column names
            val trdSymbol = row["pTrdSymbol"] ?: row["trading_symbol"] ?: row["symbol"] ?: return@mapNotNull null
            StockEntity(
                pTrdSymbol = trdSymbol,
                pSymbol = row["pSymbol"] ?: row["token"],
                pSymbolName = row["pSymbolName"] ?: row["company_name"] ?: row["name"],
                pGroup = row["pGroup"] ?: row["group"],
                pExSeg = row["pExSeg"] ?: row["exchange_segment"],
                pInstType = row["pInstType"] ?: row["instrument_type"]
            )
        }

        stockDao.deleteAll()
        // Insert in batches to avoid SQLite variable limit
        entities.chunked(500).forEach { batch ->
            stockDao.insertAll(batch)
        }
        stockDao.updateSyncTime(SyncMetadata("scripmaster", System.currentTimeMillis()))
    }

    suspend fun search(query: String): List<StockEntity> {
        if (query.length < 2) return emptyList()
        return stockDao.search(query)
    }
}
