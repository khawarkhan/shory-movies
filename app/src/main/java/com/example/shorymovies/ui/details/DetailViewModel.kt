package com.example.shorymovies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.use_case.details.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Responsible for fetching and redirecting data model to UI
 *
 * DetailViewModel, fetches Movie Details using imdb movie id from MovieDetailUseCase
 *
 * MovieDetailsUseCase in constructor is provided via DI (Dependency Inject)
 *
 **/
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieDetailsUseCase
) : ViewModel() {


    /** * vars for movie detail response  */
    private val _movieDetailMutableLiveDate = movieUseCase.movieDetailLiveData
    val movieDetailLiveData: LiveData<Resource<MovieDetails>> = _movieDetailMutableLiveDate

    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovieDetails(imdb: String) {
        movieUseCase.fetchMovieDetails(imdb)
    }

}