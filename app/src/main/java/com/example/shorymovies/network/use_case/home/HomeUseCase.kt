package com.example.shorymovies.network.use_case.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shorymovies.common.Constants
import com.example.shorymovies.common.SecurityUtils
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.network.repository.home.SuperHeroesRepository
import com.example.shorymovies.network.repository.movies.MoviesRepository
import java.util.Date
import javax.inject.Inject

/**
 * created by Khawar Habib on 25/06/2023
 *
 * UseCase for home screen to handle response from marvel apis
 *  informs ViewModel of call status ie. Loading/SuccessResponse/Failure
 *
 **/
class HomeUseCase @Inject constructor(private val moviesRepository: SuperHeroesRepository) {


    // for super heroes
    private val _superHeroMutableLiveDate = MutableLiveData<Resource<SuperHeroesResponse>>()
    val superHeroLiveData: LiveData<Resource<SuperHeroesResponse>> = _superHeroMutableLiveDate


    /**
     * Fetch super heros (comic characters from marvel api)
     */
    suspend fun fetchSuperHeroes() {


        _superHeroMutableLiveDate.value = Resource.Loading(true)

        // time stamp for api
        val ts = "${Date().time}"

        // marvel public key
        val marvelPublicKey = Constants.marvelPublicKey

        // marvel private key
        val marvelPrivateKey = Constants.marvelPrivate

        // hash used in query params
        val hash = SecurityUtils.MD5("${ts}${marvelPrivateKey}${marvelPublicKey}")

        try {
            moviesRepository.fetchSuperHeroes(hash, ts).collect { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { model ->
                        _superHeroMutableLiveDate.value = Resource.Loading(false)
                        _superHeroMutableLiveDate.value = Resource.Success(model)
                    }

                } else {
                    _superHeroMutableLiveDate.value = Resource.Loading(false)
                    _superHeroMutableLiveDate.value = Resource.Error(response.message())
                }
            }

        } catch (e: Exception) {
            _superHeroMutableLiveDate.value = Resource.Loading(false)
            _superHeroMutableLiveDate.value = Resource.Error(e.message ?: "Something went wrong!")
        }
    }

}