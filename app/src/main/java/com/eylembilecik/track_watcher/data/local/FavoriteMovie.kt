package com.eylembilecik.track_watcher.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    var season: Int = 1,
    var episode: Int = 1,
    var minute: Int = 0
)
