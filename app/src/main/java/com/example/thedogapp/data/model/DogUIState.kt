package com.example.thedogapp.data.model

data class DogUIState(
    val isLoading: Boolean = false,
    val dogs: List<Dog> = emptyList(),
    val error: String? = null
)
