package com.example.thedogapp.data

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object Home : Screens()

    @Serializable
    data class Details(val jsonStr: String) : Screens()

    @Serializable
    data object Favorites : Screens()

    @Serializable
    data object About : Screens()
}
