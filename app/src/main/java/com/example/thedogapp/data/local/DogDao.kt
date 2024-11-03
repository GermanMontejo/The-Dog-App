package com.example.thedogapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Update
    suspend fun update(dog: DogEntity)

    @Query("SELECT * FROM dogs WHERE isFavorite = true")
    fun getFavoriteDogs(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dogs")
    fun getDogs(): Flow<List<DogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>)
}
