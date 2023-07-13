package com.example.shorymovies.network.remote.movies

import com.example.shorymovies.common.Constants
import com.example.shorymovies.network.MoviesService
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.remote.BaseDataSource
import javax.inject.Inject

/**
 * created by Khawar Habib on 08/07/2023
 *
 * fetches data from remote source
 *
 * * ATTENTION: Movie Remote data source is only created for Movies listing for now
 */
class MovieRemoteDataSource @Inject constructor(
    private val apiServiceAPI: MoviesService
) : BaseDataSource() {

    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovies(character: String): Resource<List<Movie>> {
        val response = getResponse(

            /** fetch remote data via API call */
            call = {
                apiServiceAPI.fetchMovies(keyword = character)
            },

            /** do data transformation before sending back */
            transform = {
                it?.movies ?: emptyList()
            },

            /** provide common error message */
            defaultErrorMessage = Constants.commonErrorMsg
        )

        return response
    }

}