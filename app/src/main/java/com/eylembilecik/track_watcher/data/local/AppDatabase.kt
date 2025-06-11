package com.eylembilecik.track_watcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eylembilecik.track_watcher.data.model.WatchItem

@Database(entities = [WatchItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchDao(): WatchDao
}
