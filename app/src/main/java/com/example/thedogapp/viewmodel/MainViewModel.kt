package com.example.thedogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thedogapp.data.model.Dog
import com.example.thedogapp.data.model.DogUIState
import com.example.thedogapp.data.remote.NetworkResult
import com.example.thedogapp.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private var _dogState: MutableStateFlow<DogUIState> = MutableStateFlow(DogUIState())
    val dogState: StateFlow<DogUIState> = _dogState.asStateFlow()

    private var _favoritesState: MutableStateFlow<DogUIState> = MutableStateFlow(DogUIState())
    val favoritesState: StateFlow<DogUIState> = _favoritesState.asStateFlow()

    fun getDogs() {
        _dogState.value = DogUIState(isLoading = true)
        viewModelScope.launch {
            repository.fetchDogsFromLocalDB().collect { dogs ->
                if (dogs.isEmpty()) {
                    when (val result = getDogsFromRemoteSource()) {
                        is NetworkResult.Error -> _dogState.value =
                            DogUIState(error = result.message)

                        is NetworkResult.Success -> {
                            _dogState.value = DogUIState(dogs = result.data)
                        }
                    }
                } else {
                    _dogState.value = DogUIState(dogs = dogs)
                }
            }
        }
    }

    suspend fun getDogsFromRemoteSource() = repository.fetchDogsFromRemoteSource()

    fun getFavoriteDogs() {
        _favoritesState.value = DogUIState(isLoading = true)
        viewModelScope.launch {
            repository.fetchFavoriteDogsFromLocalDB().collect { favorites ->
                if (favorites.isEmpty()) {
                    DogUIState(error = "No favorite dogs added.")
                } else {
                    _favoritesState.value = DogUIState(dogs = favorites)
                }
            }
        }
    }

    fun update(dog: Dog) {
        viewModelScope.launch {
            repository.update(dog )
        }
    }
}
