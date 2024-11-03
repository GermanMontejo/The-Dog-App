package com.example.thedogapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    @SerialName("bred_for")
    val bredFor: String? = null,
    @SerialName("breed_group")
    val breedGroup: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    val description: String? = null,
    val height: Height,
    val history: String? = null,
    val id: Int,
    val image: Image,
    @SerialName("life_span")
    val lifespan: String,
    val name: String,
    val origin: String? = null,
    @SerialName("reference_image_id")
    val referenceImageId: String,
    val temperament: String? = null,
    val weight: Weight,
    val isFavorite: Boolean = false
)
