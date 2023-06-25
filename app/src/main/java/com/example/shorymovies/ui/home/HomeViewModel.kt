package com.example.shorymovies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.home.SuperHeroesResponse
import com.example.shorymovies.network.use_case.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Responsible for fetching and redirecting data model to UI
 *
 * HomeViewModel, fetches SuperHero characters list from HomeUseCase
 *
 * HomeUseCase in constructor is provided via DI (Dependency Inject)
 *
 **/
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: HomeUseCase
) : ViewModel() {


    /** * vars for movie detail response  */
    private val _superHeroMutableLiveDate = movieUseCase.superHeroLiveData
    val superHeroLiveData: LiveData<Resource<SuperHeroesResponse>> = _superHeroMutableLiveDate

    /**
     * Fetch list of movies for selected character
     */
    suspend fun fetchSuperHeroes() {
        movieUseCase.fetchSuperHeroes()
    }

    /**
     * read character's name from list
     */
    fun getCharacterName(index: Int): String? {

        val items = superHeroLiveData.value as Resource.Success<SuperHeroesResponse>
        return items.data?.let {
            it.data.heroes[index].name
        }
    }

}