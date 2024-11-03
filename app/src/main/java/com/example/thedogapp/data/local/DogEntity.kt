package com.example.thedogapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Weight

@Entity(tableName = "dogs")
data class DogEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val temperament: String,
    val image: String,
    val height: Height,
    val weight: Weight,
    val lifespan: String,
    var isFavorite: Boolean
)
