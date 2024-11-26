package com.example.thedogapp.data.model

import com.example.thedogapp.domain.model.Dog

data class DogUIState(
    val isLoading: Boolean = false,
    val dogs: List<Dog> = emptyList(),
    val error: String? = null
)
