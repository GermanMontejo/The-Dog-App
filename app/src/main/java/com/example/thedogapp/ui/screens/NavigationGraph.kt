package com.example.thedogapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.thedogapp.data.Screens
import kotlinx.serialization.json.Json



@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home,
        modifier = Modifier.then(modifier)
    ) {
        composable<Screens.Home> {
            HomeScreen(navController, modifier = Modifier)
        }
        composable<Screens.Favorites> {
            FavoritesScreen(navController = navController, modifier = Modifier)
        }
        composable<Screens.About> {
            AboutScreen(modifier = Modifier)
        }
        composable<Screens.Details> { backstackEntry ->
            val details = backstackEntry.toRoute<Screens.Details>()
            DogDetailsScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                dog = Json.decodeFromString(details.jsonStr)
            )
        }
    }
}
