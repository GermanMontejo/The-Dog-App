package com.example.thedogapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.thedogapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavoritesScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = koinViewModel()
    viewModel.getFavoriteDogs()
    val result by viewModel.favoritesState.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items (result.dogs) { dog ->
            Dog(viewModel, navController, dog, result.dogs.indexOf(dog) == 0)
        }
    }
}
