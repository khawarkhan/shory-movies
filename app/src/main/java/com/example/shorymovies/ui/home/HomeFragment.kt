package com.example.shorymovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shorymovies.common.AppDialog
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

                val homeToPageAction =
                    HomeFragmentDirections.actionCharacterToMovies(
                        /**
                         *  for testing because sometime marvel character don't have movie in omdb server
                         *
                         *  and even if you want see differnt movies of your own choice for testing, just change the name
                         *
                         * */
//                    "spider-man"
                        name
                    )
                findNavController().navigate(homeToPageAction)
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

                is Resource.Error -> AppDialog.showMessageDialog(
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
}