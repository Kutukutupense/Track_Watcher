package com.eylembilecik.track_watcher.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun DetailScreen(viewModel: MovieViewModel) {
    val movie = viewModel.selectedMovie.collectAsState().value
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(movie?.id) {
        movie?.let {
            viewModel.isFavorite(it.id) { fav ->
                isFavorite = fav
            }
        }
    }

    if (movie == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Movie not found.")
        }
        return
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
            contentDescription = movie.title ?: movie.name ?: "Movie poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title ?: movie.name ?: "No title",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Release Date: ${movie.releaseDate ?: "Unknown"}")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Rating: ${movie.voteAverage}")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = movie.overview ?: "")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val favorite = FavoriteMovie(
                    id = movie.id,
                    title = movie.title ?: movie.name ?: "No title",
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    overview = movie.overview ?: "",
                    releaseDate = movie.releaseDate ?: "",
                    season = 1,
                    episode = 1,
                    minute = 0,
                    isSeries = viewModel.isSeriesMode.value
                )

                if (isFavorite) {
                    viewModel.removeFromFavorites(favorite)
                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addToFavorites(favorite)
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
                isFavorite = !isFavorite
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isFavorite) "Delete From Favorites" else "Add To Favorites")
        }
    }
}
