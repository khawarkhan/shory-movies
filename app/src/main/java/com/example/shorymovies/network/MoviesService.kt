package com.example.shorymovies.network

import com.example.shorymovies.BuildConfig
import com.example.shorymovies.common.Constants
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.model.movies.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * created by Khawar Habib on 25/06/2023
 *
 * This is the final service, makes service calls
 *
 * - Request body
 * - Response types
 *
 * 1- Fetches movies from omdb server
 * 2- Fetches movie details from omdb server
 * 3- Fetches Super hero characters from marvel server
 *
 **/
interface MoviesService {
    /**
     * Fetch movies for keyword 's' used in title/names in omdb server
     */
    @GET(".")
    suspend fun fetchMovies(
        @Query("s") keyword: String,
        /** keeping keys in gradle.properties to make it more secure than plain constants*/
        @Query("apikey") apikey: String = BuildConfig.OMDB_API_KEY,
    ): Response<MoviesResponse>


    @GET(".")
    suspend fun fetchMovieDetails(
        @Query("i") imdbId: String,
        /** keeping keys in gradle.properties to make it more secure than plain constants*/
        @Query("apikey") apikey: String = BuildConfig.OMDB_API_KEY,
    ): Response<MovieDetails>


    @GET
    suspend fun fetchSuperHeroes(
        @Url url: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int,
    ): Response<SuperHeroesResponse>
}