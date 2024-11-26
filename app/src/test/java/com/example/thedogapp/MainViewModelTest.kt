package com.example.thedogapp

import com.example.thedogapp.data.remote.Result
import com.example.thedogapp.domain.model.Dog
import com.example.thedogapp.domain.usecase.GetDogsUseCase
import com.example.thedogapp.domain.usecase.GetFavoriteDogsUseCase
import com.example.thedogapp.domain.usecase.UpdateDogUseCase
import com.example.thedogapp.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var getDogsUseCase: GetDogsUseCase
    private lateinit var getFavoriteDogsUseCase: GetFavoriteDogsUseCase
    private lateinit var updateDogUseCase: UpdateDogUseCase

    private val dogs = listOf(
        Dog(
            height = "43 - 51 cm",
            id = 1,
            imageUrl = "https://cdn2.thedogapi.com/images/HyWNfxc47.jpg",
            lifespan = "9 - 11 years",
            name = "French Bulldog",
            temperament = "Playful, Affectionate, Keen, Sociable, Lively, Alert, Easygoing, Patient, Athletic, Bright",
            weight = "13 kg",
            isFavorite = true
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getDogsUseCase = mockk(relaxed = true)
        getFavoriteDogsUseCase = mockk(relaxed = true)
        updateDogUseCase = mockk(relaxed = true)
        viewModel = MainViewModel(getDogsUseCase, getFavoriteDogsUseCase, updateDogUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetNetworkResultSuccess() = runTest {
        coEvery { getDogsUseCase.getDogsFromDB() } returns flowOf(emptyList())
        coEvery { getDogsUseCase.getDogsFromAPI() } returns Result.Success(data = dogs)
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        assertEquals(result.isLoading, false)
        assertEquals(result.dogs, dogs)
    }

    @Test
    fun testGetNetworkResultError() = runTest {
        coEvery { getDogsUseCase.getDogsFromDB() } returns flowOf(emptyList())
        coEvery { getDogsUseCase.getDogsFromAPI() } returns Result.Error(message = "Error!")
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        assertEquals(result.isLoading, false)
        assertEquals(result.error, "Error!")
    }

    @Test
    fun testGetFromLocalDB() = runTest {
        coEvery { getDogsUseCase.getDogsFromDB() } returns flowOf(dogs)
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        assertEquals(result.isLoading, false)
        assertEquals(result.dogs, dogs)
    }
}
