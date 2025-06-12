package com.eylembilecik.track_watcher.data.repository

import com.eylembilecik.track_watcher.data.model.Movie
import com.eylembilecik.track_watcher.data.model.MovieResponse
import com.eylembilecik.track_watcher.network.TmdbApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    suspend fun getMovieDetail(movieId: Int): Movie {
        return apiService.getMovieDetail(movieId = movieId)
    }
}
