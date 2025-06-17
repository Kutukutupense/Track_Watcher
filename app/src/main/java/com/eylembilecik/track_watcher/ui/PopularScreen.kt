package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel
import com.eylembilecik.track_watcher.data.model.Movie
import com.eylembilecik.track_watcher.ui.components.MovieItem

@Composable
fun PopularScreen(
    navController: NavHostController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieResponse = viewModel.movieList.collectAsState().value
    val isSeriesMode by viewModel.isSeriesMode.collectAsState()

    LaunchedEffect(isSeriesMode) {
        viewModel.getPopularContent()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ContentToggle(
            isSeriesMode = isSeriesMode,
            onToggle = { viewModel.setSeriesMode(it) }
        )

        movieResponse?.let { response ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(response.results) { movie ->
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
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }
    }
}
