package com.example.thedogapp.domain.usecase

import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.domain.model.Dog
import com.example.thedogapp.domain.toDogDomainModel
import kotlinx.coroutines.flow.first

class GetFavoriteDogsUseCase(private val mainRepository: MainRepository) {

    suspend fun execute(): List<Dog> = mainRepository
        .fetchFavoriteDogsFromLocalDB()
        .first()
        .map {
            it.toDogDomainModel()
        }
}
