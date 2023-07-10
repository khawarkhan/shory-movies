package com.example.shorymovies.network.remote

import com.example.shorymovies.network.model.Resource
import retrofit2.Response

/**
 * created by Khawar Habib on 09/07/2023
 *
 * abstract base remote data source with common function to keep base work common
 *
 **/
abstract class BaseDataSource {

    /**
     * getResponse with higher order kotlin function to, lifts up the common
     * responsibility to fetch remote data
     *
     */
    suspend inline fun <T, P> getResponse(

        /** fetching data from source */
        call: suspend () -> Response<T>,

        /** in case each requester fun wants to transfer data before sending back*/
        transform: (T?) -> P,

        /** default error message in case exception has null message */
        defaultErrorMessage: String

    ): Resource<P> {
        return try {

            // call the func to fetch data
            val result = call.invoke()

            // check if success and result is not null
//            if (result.isSuccessful && result.body() != null) {
            if (result.isSuccessful ) {

                // transform data if needed
                val transformedData = transform(result.body())

                Resource.Success(transformedData)

            } else {
                Resource.Error(result.message() ?: defaultErrorMessage)
            }
        } catch (e: Throwable) {
            Resource.Error(e.message ?: defaultErrorMessage)
        }
    }
}