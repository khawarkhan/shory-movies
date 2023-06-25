package com.example.shorymovies.network.use_case.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shorymovies.common.Constants
import com.example.shorymovies.common.SecurityUtils
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.repository.details.MovieDetailRepository
import com.example.shorymovies.network.repository.movies.MoviesRepository
import java.util.Date
import javax.inject.Inject

/**
 * created by Khawar Habib on 25/06/2023
 *
 ** UseCase for details screen to handle response from omdb apis
 *  informs ViewModel of call status ie. Loading/SuccessResponse/Failure
 *
 **/
class MovieDetailsUseCase @Inject constructor(private val moviesRepository: MovieDetailRepository) {


    // for movie details
    private val _movieDetailMutableLiveDate = MutableLiveData<Resource<MovieDetails>>()
    val movieDetailLiveData: LiveData<Resource<MovieDetails>> = _movieDetailMutableLiveDate


    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchMovieDetails(imdb: String) {

        _movieDetailMutableLiveDate.value = Resource.Loading(true)

        try {
            moviesRepository.fetchMovieDetail(imdb).collect { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { model ->
                        _movieDetailMutableLiveDate.value = Resource.Loading(false)


                        /** parse writter, actors and Genre to be displayed in list */
                        model.actorsList = parseCommaSeparatedList(model.Actors)
                        model.genreList = parseCommaSeparatedList(model.Genre)
                        model.writerList = parseCommaSeparatedList(model.Writer)
                        model.videoUrl = Constants.VIDEO_URL

                        _movieDetailMutableLiveDate.value = Resource.Success(model)
                    }

                } else {
                    _movieDetailMutableLiveDate.value = Resource.Loading(false)
                    _movieDetailMutableLiveDate.value = Resource.Error(response.message())
                }
            }

        } catch (e: Exception) {
            _movieDetailMutableLiveDate.value = Resource.Loading(false)
            _movieDetailMutableLiveDate.value = Resource.Error(e.message ?: "Something went wrong!")
        }
    }

    private fun parseCommaSeparatedList(value: String?): List<String> {
        return value?.split(',') ?: emptyList()
    }


}