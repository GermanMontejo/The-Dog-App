package com.example.thedogapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Height(
    val imperial: String,
    val metric: String
)
