package com.eylembilecik.track_watcher.data.repository

import com.eylembilecik.track_watcher.data.model.MovieResponse
import com.eylembilecik.track_watcher.network.TmdbApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    suspend fun getPopularMovies(): MovieResponse = apiService.getPopularMovies()
    suspend fun getPopularSeries(): MovieResponse = apiService.getPopularSeries()
    suspend fun searchMovies(query: String): MovieResponse = apiService.searchMovies(query)
    suspend fun searchSeries(query: String): MovieResponse = apiService.searchSeries(query)
}
