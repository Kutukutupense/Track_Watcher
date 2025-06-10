package com.eylembilecik.track_watcher.repository

import com.eylembilecik.track_watcher.network.TmdbApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    suspend fun getPopularMovies(apiKey: String) = apiService.getPopularMovies(apiKey)
}
