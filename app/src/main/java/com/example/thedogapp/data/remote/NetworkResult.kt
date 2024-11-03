package com.example.thedogapp.data.remote

sealed class NetworkResult<T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error<T>(val message: String): NetworkResult<T>()
}
