package com.example.thedogapp.data.repository

import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.DogResponse
import com.example.thedogapp.data.remote.Result
import com.example.thedogapp.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun fetchDogsFromRemoteSource(): Result<List<DogResponse>>
    suspend fun fetchFavoriteDogsFromLocalDB(): Flow<List<DogEntity>>
    suspend fun fetchDogsFromLocalDB(): Flow<List<DogEntity>>
    suspend fun update(dog: Dog)
    suspend fun insertDogs(dogs: List<DogResponse>)
}
