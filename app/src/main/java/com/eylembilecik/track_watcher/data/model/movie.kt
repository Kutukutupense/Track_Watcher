package com.eylembilecik.track_watcher.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String
)
