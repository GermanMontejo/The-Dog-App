package com.example.thedogapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.thedogapp.data.Screens
import com.example.thedogapp.domain.model.Dog
import com.example.thedogapp.ui.theme.Color.BackgroundColorLight
import com.example.thedogapp.viewmodel.MainViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun Dogs(
    navController: NavController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        mainViewModel.getDogs()
    }
    val state by mainViewModel.dogState.collectAsStateWithLifecycle()
    AnimatedVisibility(visible = state.isLoading) {
        CircularProgressIndicator()
    }
    AnimatedVisibility(visible = state.dogs.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(state.dogs) { dog ->
                Dog(mainViewModel, navController, dog, state.dogs.indexOf(dog) == 0)
            }
        }
    }
    AnimatedVisibility(visible = state.error != null) {
        Text(
            text = "Error: ${state.error}",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Dog(
    mainViewModel: MainViewModel,
    navController: NavController,
    dog: Dog,
    isFirstItem: Boolean
) {
    val isFavorite = dog.isFavorite

    ElevatedCard(
        onClick = {
            println("tag123, dog: $dog")
            navController.navigate(Screens.Details(jsonStr = Json.encodeToString(dog))) {
                launchSingleTop = true
            }
        },
        modifier = Modifier.padding(
            start = 16.dp,
            top = if (isFirstItem) 16.dp else 0.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        colors = CardDefaults.cardColors(containerColor = BackgroundColorLight)
    ) {
        CardContent(dog, isFavorite, mainViewModel)
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun CardContent(
    dog: Dog,
    isFavorite: Boolean,
    mainViewModel: MainViewModel
) {
    Column {
        AsyncImage(
            model = dog.imageUrl,
            contentDescription = dog.name,
            modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.LightGray),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            Text(
                text = dog.name,
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                tint = if (isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                },
                contentDescription = "favorite",
                modifier = Modifier
                    .clickable {
                        mainViewModel.update(dog.copy(isFavorite = !isFavorite))
                    }
                    .padding(start = 8.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            val temperaments = dog.temperament.split(",")
            temperaments.forEachIndexed { index, temperament ->
                Text(
                    text = if (index < temperaments.size - 1) {
                        "${temperament.trim()}, "
                    } else {
                        temperament.trim()
                    }
                )
            }
        }
    }
}
