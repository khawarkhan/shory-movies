package com.example.shorymovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shorymovies.R
import com.example.shorymovies.common.DialogManager
import com.example.shorymovies.common.RecyclerItemClickListener
import com.example.shorymovies.databinding.FragmentHomeBinding
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.home.SuperHero
import com.example.shorymovies.ui.adapters.HeroesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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

    // enabling binding to avoid id based view finding, we can directly find view by using .
    private lateinit var binding: FragmentHomeBinding

    // initializing HomeViewModel
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
        context?.let {
            observeSuperHeroes()
            fetchSuperHeroes()
        }

        setupMenu()
    }


    /**
     * Initializing characters adapter to show super heroes in listing
     */
    private fun initAdapter(movies: List<SuperHero>) {
        binding.charactersRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.charactersRecyclerView.layoutManager = gridLayoutManager

            // assign heroes adapter to character recyclerview
            binding.charactersRecyclerView.adapter = HeroesAdapter(movies)
        }

        // recyclerview/listing row click listener
        binding.charactersRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context) { _, index ->

            /** Go to Movies screen, to show movies for selected hero */
            viewModel.getCharacterName(index)?.let { name ->
                openMovieListing(name)
            }
        })
    }

    /**
     * Fetch movies for given characters -> args.data
     */
    private fun fetchSuperHeroes() {
        lifecycleScope.launch {
            viewModel.fetchSuperHeroes()
        }
    }

    /**
     * Observe the movies, show in listing when returned
     */
    private fun observeSuperHeroes() {
        viewModel.superHeroLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        initAdapter(response.data.data.heroes)
                    }
                }

                is Resource.Error -> DialogManager.showMessageDialog(
                    requireContext(),
                    response.message!!
                )

                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility =
                        if (response.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }


    /**
     * Open movie listing
     */
    private fun openMovieListing(name: String) {
        /** pick super hero name and push Movie listing screen */
        val homeToPageAction =
            HomeFragmentDirections.actionCharacterToMovies(
                /**
                 *  for testing because sometime marvel character don't have movie in omdb server
                 *
                 *  and even if you want see different movies of your own choice for testing, just change the name
                 *
                 * */
                name
            )
        findNavController().navigate(homeToPageAction)
    }


    /**
     * setting up menu to allow user enter movie if didn't like any in shown listing
     */
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                /**
                 *  open Editable field to let user enter movie name if he/she didn't like one shown in listing
                 *
                 * */
                DialogManager.showAlertWithEditField(
                    requireActivity(),
                    getString(R.string.enter_movie_name),
                    getString(R.string.find),
                    getString(R.string.cancel),
                ) { movieName ->
                    openMovieListing(movieName)
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}