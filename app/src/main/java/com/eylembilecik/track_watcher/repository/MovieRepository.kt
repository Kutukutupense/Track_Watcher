package com.eylembilecik.track_watcher.data.repository

import com.eylembilecik.track_watcher.data.model.MovieResponse
import com.eylembilecik.track_watcher.network.TmdbApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    suspend fun getPopularMovies(): MovieResponse? = safeApiCall {
        apiService.getPopularMovies()
    }

    suspend fun getPopularSeries(): MovieResponse? = safeApiCall {
        apiService.getPopularSeries()
    }

    suspend fun searchMovies(query: String): MovieResponse? = safeApiCall {
        apiService.searchMovies(query)
    }

    suspend fun searchSeries(query: String): MovieResponse? = safeApiCall {
        apiService.searchSeries(query)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall()
        } catch (e: IOException) {
            null // internet yok
        } catch (e: HttpException) {
            null // server hatası
        } catch (e: Exception) {
            null
        }
    }
}
