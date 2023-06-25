package com.example.shorymovies.network.model


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Generic class type to keep return types same across the project,
 *
 *  Handles following
 *
 * 1- Success model type
 * 2- Progress model type
 * 3- Failure model type
 *
 *
 **/
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}
