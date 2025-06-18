package com.eylembilecik.track_watcher.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_items")
data class WatchItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val isMovie: Boolean,
    val season: Int?,
    val episode: Int?,
    val minute: Int?,
    val isWatched: Boolean = false
)
