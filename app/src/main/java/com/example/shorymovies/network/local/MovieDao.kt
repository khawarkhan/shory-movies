package com.example.shorymovies.network.local

import androidx.room.*
import com.example.shorymovies.network.model.movies.Movie

/**
 * created by Khawar Habib on 04/07/2023
 *
 * MovieDao with common query to be applied on Movies table
 *
 * ATTENTION: Movie DAO is only created for Movies listing for now
 **/
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_table where movies_table.movieName=:name")
    suspend fun getMoviesByName(name: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie): Long

    @Delete
    suspend fun delete(article: Movie)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllMovies()
}