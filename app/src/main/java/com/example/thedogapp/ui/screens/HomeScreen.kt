package com.example.thedogapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.thedogapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val mainViewModel: MainViewModel = koinViewModel()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Dogs(navController, mainViewModel)
    }
}
