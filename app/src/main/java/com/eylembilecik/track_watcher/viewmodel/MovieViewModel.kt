package com.eylembilecik.track_watcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.data.model.Movie
import com.eylembilecik.track_watcher.data.model.MovieResponse
import com.eylembilecik.track_watcher.data.repository.FavoriteMovieRepository
import com.eylembilecik.track_watcher.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteMovieRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _movieList = MutableStateFlow<MovieResponse?>(null)
    val movieList: StateFlow<MovieResponse?> = _movieList

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _isSeriesMode = MutableStateFlow(false)
    val isSeriesMode: StateFlow<Boolean> = _isSeriesMode

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    // Favorileri doğrudan Flow olarak veriyoruz
    val favoriteMovies: Flow<List<FavoriteMovie>> = favoriteMovieRepository.getAllFavorites()

    fun setSeriesMode(isSeries: Boolean) {
        _isSeriesMode.value = isSeries
    }

    fun getPopularContent() {
        viewModelScope.launch {
            _movieList.value = if (_isSeriesMode.value) {
                movieRepository.getPopularSeries()
            } else {
                movieRepository.getPopularMovies()
            }
        }
    }

    fun searchContent(query: String) {
        viewModelScope.launch {
            _searchResults.value = if (_isSeriesMode.value) {
                movieRepository.searchSeries(query).results
            } else {
                movieRepository.searchMovies(query).results
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun addToFavorites(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMovieRepository.addFavorite(favoriteMovie)
        }
    }

    fun removeFromFavorites(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMovieRepository.removeFavorite(favoriteMovie)
        }
    }

    fun updateFavorite(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMovieRepository.updateFavorite(favoriteMovie)
        }
    }

    fun isFavorite(movieId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = favoriteMovieRepository.isFavorite(movieId)
            onResult(result)
        }
    }
}
