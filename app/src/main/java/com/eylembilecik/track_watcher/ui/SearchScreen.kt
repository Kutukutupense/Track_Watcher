package com.eylembilecik.track_watcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel


@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MovieViewModel
) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Enter movie or series name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.searchMovies(query)
        },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)

            ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        searchResults.forEach { movie ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        viewModel.selectMovie(movie)
                        navController.navigate("details")
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                        contentDescription = null,
                        modifier = Modifier
                            .width(80.dp)
                            .height(120.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Rating: ${movie.voteAverage}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Release: ${movie.releaseDate}")
                    }
                }
            }
        }
    }
}
