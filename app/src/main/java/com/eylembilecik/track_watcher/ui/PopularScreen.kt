package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun PopularScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val movieResponse = viewModel.movieList.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getPopularMovies()
    }

    movieResponse?.let { response ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(response.results) { movie ->
                MovieItem(
                    title = movie.title,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage
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

@Composable
fun MovieItem(title: String, posterPath: String, voteAverage: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(144.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Score: $voteAverage")
            }
        }
    }
}
