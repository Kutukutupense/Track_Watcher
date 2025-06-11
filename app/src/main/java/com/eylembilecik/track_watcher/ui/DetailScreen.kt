package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    movieId: String,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieResponse = viewModel.movieList.collectAsState().value
    val movie = movieResponse?.results?.find { it.id.toString() == movieId }

    if (movie == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Movie not found.")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Release Date: ${movie.releaseDate}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rating: ${movie.voteAverage}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.overview)
    }
}
