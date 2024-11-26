package com.example.thedogapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    val id: Int,
    val name: String,
    val temperament: String,
    val height: String,
    val weight: String,
    val lifespan: String,
    val imageUrl: String,
    val isFavorite: Boolean
)
