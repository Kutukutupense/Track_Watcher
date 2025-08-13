package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eylembilecik.track_watcher.ui.components.ContentToggle
import com.eylembilecik.track_watcher.ui.components.MovieItem
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun PopularScreen(
    navController: NavHostController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieResponse by viewModel.movieList.collectAsState()
    val isSeriesMode by viewModel.isSeriesMode.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isSeriesMode) {
        viewModel.getPopularContent()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ContentToggle(
            isSeriesMode = isSeriesMode,
            onToggle = { viewModel.setSeriesMode(it) }
        )

        when {
            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage ?: "Unknown error")
                }
            }
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...")
                }
            }
            movieResponse?.results.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No data available")
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(movieResponse!!.results) { movie ->
                        MovieItem(
                            movie = movie,
                            onClick = {
                                viewModel.selectMovie(movie)
                                navController.navigate("details")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
