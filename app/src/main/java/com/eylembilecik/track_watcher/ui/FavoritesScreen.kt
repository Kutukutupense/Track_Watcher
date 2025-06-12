package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState(initial = emptyList())

    if (favoriteMovies.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("You haven't added any favorite movies yet.")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(favoriteMovies) { movie ->
                FavoriteMovieItem(movie = movie, onRemove = { viewModel.removeFromFavorites(movie) })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(movie: FavoriteMovie, onRemove: () -> Unit) {
    var season by remember { mutableStateOf(TextFieldValue(movie.season.toString())) }
    var episode by remember { mutableStateOf(TextFieldValue(movie.episode.toString())) }
    var minute by remember { mutableStateOf(TextFieldValue(movie.minute.toString())) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Rating: ${movie.voteAverage}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Release: ${movie.releaseDate}")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Where you left off:", style = MaterialTheme.typography.bodyMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = season,
                    onValueChange = { season = it },
                    label = { Text("Season") },
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                )
                OutlinedTextField(
                    value = episode,
                    onValueChange = { episode = it },
                    label = { Text("Episode") },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                )
                OutlinedTextField(
                    value = minute,
                    onValueChange = { minute = it },
                    label = { Text("Minute") },
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onRemove,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Remove from Favorites")
            }
        }
    }
}
