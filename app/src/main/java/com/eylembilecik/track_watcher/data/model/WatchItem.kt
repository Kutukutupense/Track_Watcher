package com.eylembilecik.track_watcher.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_items")
data class WatchItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val isMovie: Boolean, // true = film, false = dizi
    val season: Int?,     // film için null olabilir
    val episode: Int?,    // film için null olabilir
    val minute: Int?,     // kaldığın dakika
    val isWatched: Boolean = false // izlendi mi
)
