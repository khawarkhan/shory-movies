package com.example.shorymovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shorymovies.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint



/**
 * created by Khawar Habib on 25/06/2023
 *
 *
 * HomeFragment's responsibilities
 *
 * 1- Setup self for DI using @AndroidEntryPoint
 * 2- Setup HomeViewModel to fetch super hero characters
 * 3- Display List of characters
 *
 **/
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(layoutInflater).also {
            binding = it
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

