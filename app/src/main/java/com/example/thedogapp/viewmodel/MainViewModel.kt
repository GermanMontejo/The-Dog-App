package com.example.thedogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thedogapp.data.model.DogUIState
import com.example.thedogapp.data.remote.Result
import com.example.thedogapp.domain.model.Dog
import com.example.thedogapp.domain.usecase.GetDogsUseCase
import com.example.thedogapp.domain.usecase.GetFavoriteDogsUseCase
import com.example.thedogapp.domain.usecase.UpdateDogUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    val getDogsUseCase: GetDogsUseCase,
    val getFavoriteDogsUseCase: GetFavoriteDogsUseCase,
    val updateDogUseCase: UpdateDogUseCase
) : ViewModel() {
    private var _dogState: MutableStateFlow<DogUIState> = MutableStateFlow(DogUIState())
    val dogState: StateFlow<DogUIState> = _dogState.asStateFlow()

    private var _favoritesState: MutableStateFlow<DogUIState> = MutableStateFlow(DogUIState())
    val favoritesState: StateFlow<DogUIState> = _favoritesState.asStateFlow()

    fun getDogs() {
        _dogState.value = DogUIState(isLoading = true)
        viewModelScope.launch {
            getDogsUseCase.getDogsFromDB().collect { dogs ->
                if (dogs.isEmpty()) {
                    when (val result = getDogsUseCase.getDogsFromAPI()) {
                        is Result.Error -> _dogState.value =
                            DogUIState(error = result.message)

                        is Result.Success -> {
                            _dogState.value = DogUIState(dogs = result.data)
                        }
                    }
                } else {
                    _dogState.value = DogUIState(dogs = dogs)
                }
            }
        }
    }

    fun getFavoriteDogs() {
        _favoritesState.value = DogUIState(isLoading = true)
        viewModelScope.launch {
            val favorites = getFavoriteDogsUseCase.execute()
            if (favorites.isEmpty()) {
                DogUIState(error = "No favorite dogs added.")
            } else {
                _favoritesState.value = DogUIState(dogs = favorites)
            }
        }
    }

    fun update(dog: Dog) {
        viewModelScope.launch {
            updateDogUseCase.execute(dog)
        }
    }
}
