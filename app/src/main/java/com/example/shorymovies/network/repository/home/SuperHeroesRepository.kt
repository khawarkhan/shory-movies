package com.example.shorymovies.network.repository.home

import com.example.shorymovies.BuildConfig
import com.example.shorymovies.common.Constants
import com.example.shorymovies.network.MoviesService
import com.example.shorymovies.network.local.MovieDao
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.model.movies.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Movies repository for marvel apis
 *
 * Repositories also decides which channel to use to fetch data, Server/DB
 *
 **/
class SuperHeroesRepository @Inject constructor(
    private val apiServiceAPI: MoviesService
) {


    /**
     * Fetch super heroes list from marvel server
     */
    suspend fun fetchSuperHeroes(hash: String, ts: String): Flow<Response<SuperHeroesResponse>> {

        return flow {
            val response = apiServiceAPI.fetchSuperHeroes(
                /** keeping keys in gradle.properties to make it more secure than plain constants*/
                url = BuildConfig.MARVEL_BASE_URL,
                apiKey = BuildConfig.MARVEL_PUBLIC_KEY,
                hash,
                ts,
                100
            )
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}