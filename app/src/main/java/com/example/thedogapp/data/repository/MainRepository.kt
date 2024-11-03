package com.example.thedogapp.data.repository

import com.example.thedogapp.data.model.Dog
import com.example.thedogapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun fetchDogsFromRemoteSource(): NetworkResult<List<Dog>>
    suspend fun fetchFavoriteDogsFromLocalDB(): Flow<List<Dog>>
    suspend fun fetchDogsFromLocalDB(): Flow<List<Dog>>
    suspend fun update(dog: Dog)
    suspend fun insertDogs(dogs: List<Dog>)
}
