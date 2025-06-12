package com.eylembilecik.track_watcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteMovie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
