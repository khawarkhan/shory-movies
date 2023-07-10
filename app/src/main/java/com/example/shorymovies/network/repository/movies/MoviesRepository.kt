package com.example.shorymovies.network.repository.movies

import com.example.shorymovies.network.local.MovieDao
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.remote.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieDao: MovieDao
) {


    /**
     * cache movies in db
     */
    private suspend fun saveInCache(movies: List<Movie>?) {
        movies?.let {
            movieDao.insertMovies(movies)
        }
    }

    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovies(character: String): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            // find movies in cache by title user used to search movies for
            val localMovies = movieDao.getMoviesByName(character)

            // return cached movies if exists
            if (!localMovies.isNullOrEmpty()) {
                emit(Resource.Loading(false))
                emit(Resource.Success(localMovies))
            } else {

                // fetch from server otherwise
                val result = remoteDataSource.fetchMovies(character)

                // upon success, cache all movies
                if (result is Resource.Success) {

                    // do data manipulation - add title to each movie object and save in
                    // cache for future use
                    result.data?.let { movies ->
                        // add searched movie name in each movie and save cache
                        movies.map {
                            it.movieName = character
                            return@map it

                        }.also { mov ->
                            // once done mapping movie name, save in db
                            saveInCache(mov)
                        }
                    }
                }

                emit(Resource.Loading(false))

                emit(result)
            }

        }.flowOn(Dispatchers.IO)
    }


}