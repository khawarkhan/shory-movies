package com.example.shorymovies.network.use_case.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.repository.movies.MoviesRepository
import javax.inject.Inject

/**
 * created by Khawar Habib on 25/06/2023
 *
 * UseCase for Movie listing screen to handle response from omdb apis
 *  informs ViewModel of call status ie. Loading/SuccessResponse/Failure
 *
 **/
class MovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {


    // for movies list
    private val _movieMutableLiveDate = MutableLiveData<Resource<ArrayList<Movie>>>()
    val movieLiveData: LiveData<Resource<ArrayList<Movie>>> = _movieMutableLiveDate


    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovies(character: String) {

        _movieMutableLiveDate.value = Resource.Loading(true)

        try {
            moviesRepository.fetchMovies(character).collect { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { model ->
                        _movieMutableLiveDate.value = Resource.Loading(false)
                        _movieMutableLiveDate.value = Resource.Success(model.movies)
                    }

                } else {
                    _movieMutableLiveDate.value = Resource.Loading(false)
                    _movieMutableLiveDate.value = Resource.Error(response.message())
                }
            }

        } catch (e: Exception) {
            _movieMutableLiveDate.value = Resource.Loading(false)
            _movieMutableLiveDate.value = Resource.Error(e.message ?: "Something went wrong!")
        }
    }


}