package com.example.thedogapp

import com.example.thedogapp.data.model.Dog
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Image
import com.example.thedogapp.data.model.Weight
import com.example.thedogapp.data.remote.NetworkResult
import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
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

    private lateinit var repository: MainRepository
    private lateinit var viewModel: MainViewModel


    private val dogs = listOf(
        Dog(
            height = Height(imperial = "17 - 20", metric = "43 - 51"),
            id = 1,
            image = Image(
                id = "1",
                url = "https://cdn2.thedogapi.com/images/HyWNfxc47.jpg",
                width = 740,
                height = 430
            ),
            lifespan = "9 - 11 years",
            name = "French Bulldog",
            referenceImageId = "HyWNfxc47",
            temperament = "Playful, Affectionate, Keen, Sociable, Lively, Alert, Easygoing, Patient, Athletic, Bright",
            weight = Weight(
                imperial = "28",
                metric = "13"
            )
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk(relaxed = true)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetNetworkResultSuccess() = runTest {
        coEvery { repository.fetchDogsFromLocalDB() } returns flowOf(emptyList())
        coEvery { repository.fetchDogsFromRemoteSource() } returns NetworkResult.Success(data = dogs)
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        assertEquals(result.isLoading, false)
        assertEquals(result.dogs, dogs)
    }

    @Test
    fun testGetNetworkResultError() = runTest {
        coEvery { repository.fetchDogsFromLocalDB() } returns flowOf(emptyList())
        coEvery { repository.fetchDogsFromRemoteSource() } returns NetworkResult.Error(message = "Error!")
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        assertEquals(result.isLoading, false)
        assertEquals(result.error, "Error!")
    }

    @Test
    fun testGetFromLocalDB() = runTest {
        coEvery { repository.fetchDogsFromLocalDB() } returns flowOf(dogs)
        viewModel.getDogs()
        advanceUntilIdle()
        val result = viewModel.dogState.first()
        coVerify(exactly = 0) { viewModel.getDogsFromRemoteSource() }
        assertEquals(result.isLoading, false)
        assertEquals(result.dogs, dogs)
    }
}
