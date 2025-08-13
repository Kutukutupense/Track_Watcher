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
import java.io.IOException
import retrofit2.HttpException
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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val favoriteMovies: Flow<List<FavoriteMovie>> = favoriteMovieRepository.getAllFavorites()

    private val _searchFavoritesQuery = MutableStateFlow("")
    val searchFavoritesQuery: StateFlow<String> = _searchFavoritesQuery

    val filteredFavoriteMovies: Flow<List<FavoriteMovie>> = combine(
        favoriteMovies,
        isSeriesMode,
        _searchFavoritesQuery
    ) { favorites, isSeries, query ->
        favorites
            .filter { it.isSeries == isSeries }
            .filter { it.title.contains(query, ignoreCase = true) }
    }

    fun setSearchFavoritesQuery(query: String) {
        _searchFavoritesQuery.value = query
    }

    fun setSeriesMode(isSeries: Boolean) {
        _isSeriesMode.value = isSeries
    }

    fun getPopularContent() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = if (_isSeriesMode.value) {
                    movieRepository.getPopularSeries()
                } else {
                    movieRepository.getPopularMovies()
                }

                if (response != null) {
                    _movieList.value = response
                } else {
                    _errorMessage.value = "No internet connection"
                }
            } catch (e: IOException) {
                _errorMessage.value = "No internet connection"
            } catch (e: HttpException) {
                _errorMessage.value = "Server error: ${e.code()}"
            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchContent(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = if (_isSeriesMode.value) {
                    movieRepository.searchSeries(query)
                } else {
                    movieRepository.searchMovies(query)
                }

                if (response != null) {
                    _searchResults.value = response.results
                } else {
                    _errorMessage.value = "No internet connection"
                }
            } catch (e: IOException) {
                _errorMessage.value = "No internet connection"
            } catch (e: HttpException) {
                _errorMessage.value = "Server error: ${e.code()}"
            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error"
            } finally {
                _isLoading.value = false
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
