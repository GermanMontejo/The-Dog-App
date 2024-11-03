package com.example.thedogapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
