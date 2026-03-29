package com.example.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks WHERE pTrdSymbol LIKE '%' || :query || '%' OR pSymbolName LIKE '%' || :query || '%' LIMIT 20")
    suspend fun search(query: String): List<StockEntity>

    @Query("SELECT COUNT(*) FROM stocks")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stocks: List<StockEntity>)

    @Query("DELETE FROM stocks")
    suspend fun deleteAll()

    @Query("SELECT timestamp FROM sync_metadata WHERE `key` = :key")
    suspend fun getLastSyncTime(key: String = "scripmaster"): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSyncTime(metadata: SyncMetadata)
}
