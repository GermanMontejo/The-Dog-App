package com.example.thedogapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

@Suppress("MatchingDeclarationName")
data class NavigationBarItem<T>(
    var name: String,
    var route: T,
    var icon: ImageVector,
)

val navBarItems = listOf(
    NavigationBarItem("Home", Screens.Home, Icons.Default.Home),
    NavigationBarItem("Favorites", Screens.Favorites, Icons.Default.Favorite),
    NavigationBarItem("About", Screens.About, Icons.Default.Info),
)
