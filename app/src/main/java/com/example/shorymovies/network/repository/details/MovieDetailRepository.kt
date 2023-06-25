package com.example.shorymovies.network.repository.details

import com.example.shorymovies.network.MoviesService
import com.example.shorymovies.network.model.details.MovieDetails
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
class MovieDetailRepository @Inject constructor(
    private val apiServiceAPI: MoviesService
) {


    /**
     * Fetch details of selected imdb id of selected movie
     */
    suspend fun fetchMovieDetail(imdb: String): Flow<Response<MovieDetails>> {
        return flow {
            val movies = apiServiceAPI.fetchMovieDetails(imdbId = imdb)
            emit(movies)
        }
    }
}