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
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.data.model.Movie


@Composable
fun DetailScreen(viewModel: MovieViewModel) {
    val movie = viewModel.selectedMovie.collectAsState().value
    var isFavorite by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val favorite = FavoriteMovie(
                    id = movie.id,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    overview = movie.overview,
                    releaseDate = movie.releaseDate
                )
                if (isFavorite) {
                    viewModel.removeFromFavorites(favorite)
                } else {
                    viewModel.addToFavorites(favorite)
                }
                isFavorite = !isFavorite
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isFavorite) "Delete From Favorites" else "Add To Favorites")
        }
    }
}
