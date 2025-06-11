package com.eylembilecik.track_watcher.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Popular.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Popular.route) {
            PopularScreen(navController)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen()
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            DetailScreen(movieId = movieId ?: "")
        }
    }
}

