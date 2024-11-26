package com.example.thedogapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thedogapp.domain.model.Dog


@Composable
fun DogDetailsScreen(onBackPressed: () -> Unit, dog: Dog) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = dog.imageUrl,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = dog.name,
        )

        val details = mapOf(
            "Breed" to dog.name,
            "Temperament" to dog.temperament,
            "Height" to "${dog.height} cm",
            "Weight" to "${dog.weight} kg",
            "Lifespan" to dog.lifespan
        )
        ItemRow(details)
        Text(
            text = "< Back",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.clickable { onBackPressed() })
    }
}


@Composable
fun ItemRow(details: Map<String, String>) {
    details.entries.forEach { detail ->
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = detail.key,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = detail.value,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(2f)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp), thickness = 1.dp
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}
