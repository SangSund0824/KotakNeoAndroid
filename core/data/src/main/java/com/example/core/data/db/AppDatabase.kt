package com.example.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StockEntity::class, SyncMetadata::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}
