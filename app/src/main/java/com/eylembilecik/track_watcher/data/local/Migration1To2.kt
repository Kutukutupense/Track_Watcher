package com.eylembilecik.track_watcher.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase



val MIGRATION_1_TO_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("ALTER TABLE favorite_movies ADD COLUMN is_series INTEGER NOT NULL DEFAULT 0")
    }
}
