package com.eylembilecik.track_watcher.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.data.local.FavoriteMovie
import com.eylembilecik.track_watcher.ui.components.ContentToggle
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState(initial = emptyList())
    val isSeriesMode by viewModel.isSeriesMode.collectAsState()
    val context = LocalContext.current

    // Diziler veya filmler olarak filtrele
    val filteredFavorites = favoriteMovies.filter { it.isSeries == isSeriesMode }

    Column(modifier = Modifier.fillMaxSize()) {

        // Filmler / Diziler toggle butonu
        ContentToggle(
            isSeriesMode = isSeriesMode,
            onToggle = { viewModel.setSeriesMode(it) }
        )

        if (filteredFavorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "You haven't added any ${if (isSeriesMode) "TV series" else "movies"} yet.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(filteredFavorites) { movie ->
                    FavoriteMovieItem(
                        movie = movie,
                        onRemove = { viewModel.removeFromFavorites(movie) },
                        onUpdate = { updatedMovie ->
                            viewModel.updateFavorite(updatedMovie)
                            Toast.makeText(context, "Progress saved", Toast.LENGTH_SHORT).show()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: FavoriteMovie,
    onRemove: () -> Unit,
    onUpdate: (FavoriteMovie) -> Unit
) {
    var season by remember { mutableStateOf(TextFieldValue(movie.season.toString())) }
    var episode by remember { mutableStateOf(TextFieldValue(movie.episode.toString())) }
    var minute by remember { mutableStateOf(TextFieldValue(movie.minute.toString())) }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = episode,
                    onValueChange = { episode = it },
                    label = { Text("Episode") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = minute,
                    onValueChange = { minute = it },
                    label = { Text("Minute") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onRemove,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Remove")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        val updatedMovie = movie.copy(
                            season = season.text.toIntOrNull() ?: 0,
                            episode = episode.text.toIntOrNull() ?: 0,
                            minute = minute.text.toIntOrNull() ?: 0
                        )
                        onUpdate(updatedMovie)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save Progress")
                }
            }
        }
    }
}
