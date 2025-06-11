package com.eylembilecik.track_watcher.ui

import androidx.annotation.DrawableRes
import com.eylembilecik.track_watcher.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Popular : BottomNavItem("popular", "Popular", R.drawable.ic_popular)
    object Favorites : BottomNavItem("favorites", "Favorites", R.drawable.ic_favorite)
    object Search : BottomNavItem("search", "Search", R.drawable.ic_search)
}
