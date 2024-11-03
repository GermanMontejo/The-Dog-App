package com.example.thedogapp.data.repository

import com.example.thedogapp.BuildConfig
import com.example.thedogapp.data.local.DogDao
import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.Dog
import com.example.thedogapp.data.model.Image
import com.example.thedogapp.data.remote.ApiService
import com.example.thedogapp.data.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class MainRepositoryImpl(private val apiService: ApiService, private val dogDao: DogDao) :
    MainRepository {
    override suspend fun fetchDogsFromRemoteSource(): NetworkResult<List<Dog>> =
        withContext(Dispatchers.IO) {
            val response = apiService.getDogs(BuildConfig.API_KEY)
            val body = response.body()
            if (response.isSuccessful && !body.isNullOrEmpty()) {
                insertDogs(dogs = body)
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(response.message())
            }
        }

    override suspend fun fetchFavoriteDogsFromLocalDB(): Flow<List<Dog>> =
        withContext(Dispatchers.IO) {
            dogDao.getFavoriteDogs().map { dogs ->
                dogs.map { dog ->
                    Dog(
                        id = dog.id,
                        name = dog.name,
                        temperament = dog.temperament,
                        height = dog.height,
                        weight = dog.weight,
                        lifespan = dog.lifespan,
                        image = Image(url = dog.image, id = "", width = 0, height = 0),
                        referenceImageId = "",
                        isFavorite = dog.isFavorite
                    )
                }
            }.onEach { dogs ->
                if (dogs.isEmpty()) {
                    fetchDogsFromRemoteSource()
                }
            }
        }


    override suspend fun fetchDogsFromLocalDB(): Flow<List<Dog>> = withContext(Dispatchers.IO) {
        dogDao.getDogs().map { dogs ->
            dogs.map { dog ->
                Dog(
                    id = dog.id,
                    name = dog.name,
                    temperament = dog.temperament,
                    height = dog.height,
                    weight = dog.weight,
                    lifespan = dog.lifespan,
                    image = Image(url = dog.image, id = "", width = 0, height = 0),
                    referenceImageId = "",
                    isFavorite = dog.isFavorite
                )
            }
        }
    }

    override suspend fun update(dog: Dog) = withContext(Dispatchers.IO) {
        val dogEntity = DogEntity(
            id = dog.id,
            name = dog.name,
            temperament = dog.temperament.orEmpty(),
            height = dog.height,
            weight = dog.weight,
            lifespan = dog.lifespan,
            image = dog.image.url,
            isFavorite = dog.isFavorite
        )
        dogDao.update(dogEntity)
    }

    override suspend fun insertDogs(dogs: List<Dog>) {
        val dogEntities = dogs.map { dog ->
            DogEntity(
                id = dog.id,
                name = dog.name,
                temperament = dog.temperament.orEmpty(),
                height = dog.height,
                weight = dog.weight,
                image = dog.image.url,
                lifespan = dog.lifespan,
                isFavorite = dog.isFavorite
            )
        }
        dogDao.insertDogs(dogEntities)
    }
}
