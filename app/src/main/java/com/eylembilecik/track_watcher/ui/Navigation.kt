package com.eylembilecik.track_watcher.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eylembilecik.track_watcher.viewmodel.MovieViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Popular.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Popular.route) {
            PopularScreen(navController, viewModel)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen(viewModel)
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen(navController, viewModel)
        }
        composable("details") {
            DetailScreen(viewModel)
        }
    }
}


