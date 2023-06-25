package com.example.shorymovies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shorymovies.databinding.FragmentHomeBinding
import com.example.shorymovies.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * created by Khawar Habib on 25/06/2023
 *
 *
 * MovieDetailsFragment's responsibilities
 *
 * 1- Setup self for DI using @AndroidEntryPoint
 * 2- Setup HomeViewModel to fetch list of movies for selected character ie. Spider-man
 * 3- Display Movies in list
 *
 **/
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel by viewModels<DetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMovieDetailsBinding.inflate(layoutInflater).also {
            binding = it
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

