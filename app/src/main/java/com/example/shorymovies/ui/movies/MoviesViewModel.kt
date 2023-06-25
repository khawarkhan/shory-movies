package com.example.shorymovies.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.use_case.movies.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

/**
 * created by Khawar Habib on 25/06/2023
 *
 * Responsible for fetching and redirecting data model to UI
 *
 * MoviesViewModel, fetches Movie Details using movie/title provided from MovieUseCase
 *
 * MoviesViewModel in constructor is provided via DI (Dependency Inject)
 *
 * 1- Handles data fetch
 * 2- Finds IMDB Movie id to be used for next action
 * 3- Picks Random IMDB Movie id for suggesting random movie to User
 *
 **/
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {


    /** * vars for movie list  */
    private val _movieMutableLiveDate = movieUseCase.movieLiveData
    val movieLiveData: LiveData<Resource<ArrayList<Movie>>> = _movieMutableLiveDate

    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovies(character: String) {
        movieUseCase.fetchMovies(character)
    }


    /**
     * Function to get IMDB movie id
     */
    fun getIMDBId(index: Int): String? {
        val items = movieLiveData.value as Resource<ArrayList<Movie>>
        return items.data?.let {
            it[index].imdbID
        }
    }


    /**
     * get random movie id (imdb)
     */
    fun getRandomMovieId(): Movie? {

        val items = movieLiveData.value as Resource<ArrayList<Movie>>

        return items.data?.let {
            val index = Random.nextInt(0, items.data.size)
            it[index]
        }
    }
}