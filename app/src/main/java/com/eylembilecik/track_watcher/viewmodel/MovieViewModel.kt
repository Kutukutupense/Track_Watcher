package com.eylembilecik.track_watcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eylembilecik.track_watcher.network.TmdbApiService
import com.eylembilecik.track_watcher.data.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val apiService: TmdbApiService
) : ViewModel() {

    private val _movieList = MutableStateFlow<MovieResponse?>(null)
    val movieList: StateFlow<MovieResponse?> = _movieList

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = apiService.getPopularMovies()
                _movieList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
