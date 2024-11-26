package com.example.thedogapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thedogapp.data.local.DogDao
import com.example.thedogapp.data.local.DogDatabase
import com.example.thedogapp.data.local.DogEntity
import com.example.thedogapp.data.model.Height
import com.example.thedogapp.data.model.Weight
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DogDaoTest {
    private lateinit var dao: DogDao
    private lateinit var database: DogDatabase
    private val dog = DogEntity(
        id = 1,
        name = "Test",
        temperament = "Happy",
        imageUrl = "test",
        height = Height(imperial = "12", metric = "12"),
        weight = Weight(imperial = "12", metric = "12"),
        lifespan = "12",
        isFavorite = true
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DogDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dogDao()
    }

    @Test
    fun testAddToFavorites() = runTest {
        dao.insertDogs(listOf(dog))
        val result = dao.getFavoriteDogs().first()
        assertTrue(result[0].isFavorite)
    }

    @Test
    fun testInsertDogsAndReadData() = runTest {
        dao.insertDogs(listOf(dog))
        val result = dao.getDogs().first()
        assertNotNull(result[0])
    }

    @After
    fun tearDown() {
        database.close()
    }
}