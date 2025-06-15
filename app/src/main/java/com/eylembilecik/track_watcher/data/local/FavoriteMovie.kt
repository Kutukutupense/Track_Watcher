package com.eylembilecik.track_watcher.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "season")
    var season: Int = 1,

    @ColumnInfo(name = "episode")
    var episode: Int = 1,

    @ColumnInfo(name = "minute")
    var minute: Int = 0,

    @ColumnInfo(name = "is_series")
    var isSeries: Boolean = false
)
