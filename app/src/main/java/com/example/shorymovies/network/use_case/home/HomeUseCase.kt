package com.example.shorymovies.network.use_case.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shorymovies.BuildConfig
import com.example.shorymovies.common.Constants
import com.example.shorymovies.common.SecurityUtils
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.repository.home.SuperHeroesRepository
import retrofit2.Response
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

        /** don't refetch super heroes if already exists*/
        if(_superHeroMutableLiveDate.value is Resource.Success<SuperHeroesResponse>) {
            if(_superHeroMutableLiveDate.value?.data != null) {
                return
            }
        }

        _superHeroMutableLiveDate.value = Resource.Loading(true)

        // time stamp for api
        val ts = "${Date().time}"

        // marvel public key
        val marvelPublicKey = BuildConfig.MARVEL_PUBLIC_KEY

        // marvel private key
        val marvelPrivateKey = BuildConfig.MARVEL_PRIVATE_KEY

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