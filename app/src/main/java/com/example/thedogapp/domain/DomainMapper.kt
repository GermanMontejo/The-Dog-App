package com.example.thedogapp.domain

import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.DogResponse
import com.example.thedogapp.domain.model.Dog

fun DogResponse.toDogDomainModel() = Dog(
    id = this.id,
    name = this.name,
    temperament = this.temperament ?: "N/A",
    height = this.height.metric,
    weight = this.weight.metric,
    isFavorite = this.isFavorite,
    imageUrl = this.image.url,
    lifespan = this.lifespan
)

fun DogEntity.toDogDomainModel() = Dog(
    id = this.id,
    name = this.name,
    temperament = this.temperament,
    height = this.height.metric,
    weight = this.weight.metric,
    isFavorite = this.isFavorite,
    imageUrl = this.imageUrl,
    lifespan = this.lifespan
)
