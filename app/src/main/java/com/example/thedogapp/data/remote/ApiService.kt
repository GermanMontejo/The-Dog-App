package com.example.thedogapp.data.remote

import com.example.thedogapp.data.model.DogResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("/v1/breeds")
    suspend fun getDogs(@Header("x-api-key") apiKey: String): Response<List<DogResponse>>
}
