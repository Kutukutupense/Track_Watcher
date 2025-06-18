package com.eylembilecik.track_watcher.data.repository

import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.data.local.FavoriteMovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return favoriteMovieDao.getAllFavorites()
    }

    suspend fun addFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.insertFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.deleteFavorite(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isMovieFavorite(movieId)
    }

    suspend fun updateFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.updateFavorite(movie)
    }
}
