package com.eylembilecik.track_watcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eylembilecik.track_watcher.data.model.MovieResponse
import com.eylembilecik.track_watcher.data.repository.MovieRepository
import com.eylembilecik.track_watcher.data.repository.FavoriteMovieRepository
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _movieList = MutableStateFlow<MovieResponse?>(null)
    val movieList: StateFlow<MovieResponse?> = _movieList.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    val favoriteMovies: Flow<List<FavoriteMovie>> = favoriteRepository.getAllFavorites()

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies()
                _movieList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = movieRepository.searchMovies(query)
                _searchResults.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
                _searchResults.value = emptyList()
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun addToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(movie)
        }
    }

    fun removeFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(movie)
        }
    }

    fun isFavorite(movieId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = favoriteRepository.isFavorite(movieId)
            onResult(result)
        }
    }
    fun updateFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteRepository.updateFavorite(movie)
        }
    }

}
