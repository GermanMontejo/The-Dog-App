package com.example.thedogapp.domain.usecase

import com.example.thedogapp.data.remote.Result
import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.domain.model.Dog
import com.example.thedogapp.domain.toDogDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class GetDogsUseCase(private val mainRepository: MainRepository) {
    suspend fun execute(): Result<List<Dog>> {
        val dogs = mainRepository.fetchDogsFromLocalDB().first()
        return if (dogs.isNotEmpty()) {
            Result.Success(dogs.map { it.toDogDomainModel() })
        } else {
            when (val result = mainRepository.fetchDogsFromRemoteSource()) {
                is Result.Error -> Result.Error(result.message)
                is Result.Success -> {
                    Result.Success(result.data.map { it.toDogDomainModel() })
                }
            }
        }
    }

    suspend fun getDogsFromDB(): Flow<List<Dog>> = mainRepository
        .fetchDogsFromLocalDB()
        .map { dogs ->
            dogs.map { dog ->
                dog.toDogDomainModel()
            }
        }

    suspend fun getDogsFromAPI(): Result<List<Dog>> = when (val result = mainRepository.fetchDogsFromRemoteSource()) {
        is Result.Error -> Result.Error(result.message)
        is Result.Success -> {
            Result.Success(result.data.map { it.toDogDomainModel() })
        }
    }
}
