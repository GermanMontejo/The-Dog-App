package com.example.thedogapp.domain.usecase

import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.domain.model.Dog

class UpdateDogUseCase(private val mainRepository: MainRepository) {
    suspend fun execute(dog: Dog) {
        mainRepository.update(dog)
    }
}
