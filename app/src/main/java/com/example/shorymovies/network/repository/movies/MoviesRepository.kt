package com.example.shorymovies.network.repository.movies

import com.example.shorymovies.common.Constants
import com.example.shorymovies.network.MoviesService
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.model.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Movies repository for omdb apis
 *
 * * Repositories also decides which channel to use to fetch data, Server/DB
 *
 **/
class MoviesRepository @Inject constructor(
    private val apiServiceAPI: MoviesService
) {


    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovies(character: String): Flow<Response<MoviesResponse>> {
        return flow {
            val movies = apiServiceAPI.fetchMovies(keyword = character)
            print(movies)
            emit(movies)
        }
    }

}