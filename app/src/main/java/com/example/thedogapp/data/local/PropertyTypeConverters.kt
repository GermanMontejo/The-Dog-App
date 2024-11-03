package com.example.thedogapp.data.local

import androidx.room.TypeConverter
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Weight
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PropertyTypeConverters {
    @TypeConverter
    fun heightToString(height: Height): String = Json.encodeToString(height)

    @TypeConverter
    fun stringToHeight(height: String): Height = Json.decodeFromString(height)

    @TypeConverter
    fun weightToString(weight: Weight): String = Json.encodeToString(weight)

    @TypeConverter
    fun stringToWeight(weight: String): Weight = Json.decodeFromString(weight)
}
