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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.eylembilecik.track_watcher.ui.components.MovieItem
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel


@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isSeriesMode by viewModel.isSeriesMode.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        ContentToggle(
            isSeriesMode = isSeriesMode,
            onToggle = { viewModel.setSeriesMode(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Enter movie or series name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.searchContent(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(searchResults) { movie ->
                MovieItem(
                    movie = movie,
                    onClick = {
                        viewModel.selectMovie(movie)
                        navController.navigate("details")
                    }
                )
            }
        }
    }
}
