package com.example.thedogapp.data.repository

import com.example.thedogapp.BuildConfig
import com.example.thedogapp.data.local.DogDao
import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.DogResponse
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Weight
import com.example.thedogapp.data.remote.ApiService
import com.example.thedogapp.data.remote.Result
import com.example.thedogapp.domain.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class MainRepositoryImpl(private val apiService: ApiService, private val dogDao: DogDao) :
    MainRepository {
    override suspend fun fetchDogsFromRemoteSource(): Result<List<DogResponse>> =
        withContext(Dispatchers.IO) {
            val response = apiService.getDogs(BuildConfig.API_KEY)
            val body = response.body()
            if (response.isSuccessful && !body.isNullOrEmpty()) {
                insertDogs(dogs = body)
                Result.Success(body)
            } else {
                Result.Error(response.message())
            }
        }

    override suspend fun fetchFavoriteDogsFromLocalDB(): Flow<List<DogEntity>> =
        withContext(Dispatchers.IO) {
            dogDao.getFavoriteDogs()
        }


    override suspend fun fetchDogsFromLocalDB(): Flow<List<DogEntity>> = withContext(Dispatchers.IO) {
        dogDao.getDogs()
    }

    override suspend fun update(dog: Dog) = withContext(Dispatchers.IO) {
        val dogEntity = DogEntity(
            id = dog.id,
            name = dog.name,
            temperament = dog.temperament,
            height = Height(metric = dog.height, imperial = ""),
            weight = Weight(metric = dog.weight, imperial = ""),
            lifespan = dog.lifespan,
            imageUrl = dog.imageUrl,
            isFavorite = dog.isFavorite
        )
        dogDao.update(dogEntity)
    }

    override suspend fun insertDogs(dogs: List<DogResponse>) {
        val dogEntities = dogs.map { dog ->
            DogEntity(
                id = dog.id,
                name = dog.name,
                temperament = dog.temperament.orEmpty(),
                height = dog.height,
                weight = dog.weight,
                imageUrl = dog.image.url,
                lifespan = dog.lifespan,
                isFavorite = dog.isFavorite
            )
        }
        dogDao.insertDogs(dogEntities)
    }
}
