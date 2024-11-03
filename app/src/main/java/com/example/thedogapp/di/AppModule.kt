package com.example.thedogapp.di

import androidx.room.Room
import com.example.thedogapp.data.local.DogDatabase
import com.example.thedogapp.data.remote.ApiService
import com.example.thedogapp.data.repository.MainRepository
import com.example.thedogapp.data.repository.MainRepositoryImpl
import com.example.thedogapp.viewmodel.MainViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    viewModel<MainViewModel> { MainViewModel(get()) }
    single<MainRepository> {
        MainRepositoryImpl(get(), get())
    }
    single<Retrofit> {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            DogDatabase::class.java,
            "dogs-db"
        ).build()
    }
    single {
        get<DogDatabase>().dogDao()
    }
}
