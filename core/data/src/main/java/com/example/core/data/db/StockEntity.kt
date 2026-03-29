package com.example.core.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class StockEntity(
    @PrimaryKey val pTrdSymbol: String,
    val pSymbol: String?,
    val pSymbolName: String?,
    val pGroup: String?,
    val pExSeg: String?,
    val pInstType: String?
)

@Entity(tableName = "sync_metadata")
data class SyncMetadata(
    @PrimaryKey val key: String,
    val timestamp: Long
)
