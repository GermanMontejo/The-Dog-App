package com.example.thedogapp

import com.example.thedogapp.data.local.DogDao
import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.Dog
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Weight
import com.example.thedogapp.data.remote.ApiService
import com.example.thedogapp.data.remote.NetworkResult
import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.data.repository.MainRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MainRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var dao: DogDao

    @MockK(relaxed = true)
    private lateinit var api: ApiService

    private lateinit var repository: MainRepository

    private val dog = Dog(
        id = 1,
        name = "Test",
        temperament = "Happy",
        image = mockk(relaxed = true),
        height = Height(imperial = "12", metric = "12"),
        weight = Weight(imperial = "12", metric = "12"),
        lifespan = "12",
        referenceImageId = ""
    )

    private val dogEntity = DogEntity(
        id = 2,
        name = "Test",
        temperament = "Happy",
        image = "Test",
        height = Height(imperial = "12", metric = "12"),
        weight = Weight(imperial = "12", metric = "12"),
        lifespan = "12",
        isFavorite = false
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MainRepositoryImpl(api, dao)
    }

    @Test
    fun fetchDogsFromRemoteSource() = runTest {
        coEvery { api.getDogs(any()) } returns Response.success(listOf(dog))
        val result = repository.fetchDogsFromRemoteSource()
        assertNotNull((result as NetworkResult.Success).data)
    }

    @Test
    fun fetchDogsFromLocalDB() = runTest {
        coEvery { dao.getDogs() } returns flowOf(listOf(dogEntity))
        val result = repository.fetchDogsFromLocalDB().first()
        assertNotNull(result[0])
    }
}
