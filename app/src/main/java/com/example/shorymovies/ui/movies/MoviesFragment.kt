package com.example.shorymovies.ui.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shorymovies.R
import com.example.shorymovies.common.Constants
import com.example.shorymovies.common.DialogManager
import com.example.shorymovies.common.DialogManager.showAlertDialog
import com.example.shorymovies.common.DialogManager.showMessageDialog
import com.example.shorymovies.common.RecyclerItemClickListener
import com.example.shorymovies.databinding.FragmentMoviesBinding
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.movies.Movie
import com.example.shorymovies.ui.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * created by Khawar Habib on 25/06/2023
 *
 *
 * MoviesFragment's responsibilities
 *
 * 1- Setup self for DI using @AndroidEntryPoint
 * 2- Setup HomeViewModel to fetch list of movies for selected character ie. Spider-man
 * 3- Display Movies in list
 *
 **/
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    /** args object used to receive super hero name from previous screen (ie. in this case, HomeFragment)*/
    val args: MoviesFragmentArgs by navArgs()
    private lateinit var binding: FragmentMoviesBinding

    /** initializing MoviesViewModel to fetch and display movies list */
    private val viewModel by viewModels<MoviesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoviesBinding.inflate(layoutInflater).also {
            binding = it
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            observeMovies()
            fetchMovies()
        }
    }


    /**
     * Initialize Movies adapter to show movies in listing
     */
    private fun initAdapter(movies: List<Movie>) {

        // prompt user if no content found for selected character name
        if (movies.isEmpty()) {
            val msg = String.format(
                binding.root.context.getString(R.string.empty_data),
                args.data
            )

            binding.textViewAlert.text = msg
            binding.textViewAlert.visibility = View.VISIBLE
            showMessageDialog(requireContext(), msg) { _, _ ->
                findNavController().popBackStack()
            }

            return
        }


        // assigning adapter to listing (Recyclerview)
        binding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MoviesAdapter(movies)
        }

        // recyclerview item click listener
        binding.moviesRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context) { _, index ->

            /** Open detail screen upon movie selection, make sure imdb id is not null */
            viewModel.getIMDBId(index)?.let {
                val homeToPageAction =
                    MoviesFragmentDirections.actionMovieDetails(it)
                findNavController().navigate(homeToPageAction)
            }
        })

        /** Check if movie suggestion is allowed */
        if (Constants.allowMovieSuggestion)
            promptUserForRandomMovie()
    }


    /** handle object and _runnable to handle random movie suggestion to use */
    var handle: Handler = Handler(Looper.getMainLooper())

    private var _runnable = Runnable {
        viewModel.getRandomMovieId()?.let { movie ->

            // show suggestion popup if user is still on movies screen
            if (this.isAdded) {
                val movieMessage = String.format(
                    binding.root.context.getString(R.string.let_watch_movie),
                    movie.Title
                )

                /** show Dialog/Popup to user after randomly find a movie to be watched! */
                showAlertDialog(
                    requireContext(),
                    null,
                    movieMessage,
                    { _, _ ->
                        /** take movies' imdb id and move to detail screen to watch movie */
                        val homeToPageAction =
                            MoviesFragmentDirections.actionMovieDetails(movie.imdbID)
                        findNavController().navigate(homeToPageAction)
                    },
                    { _, _ ->
                        // do nothing
                    },
                )
            }
        }
    }

    /**
     * suggest random movie name to user
     */
    private fun promptUserForRandomMovie() {
        handle.postDelayed(_runnable, Constants.suggestionTimeInterval)
    }

    /**
     * Fetch movies for given characters -> args.data
     */
    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.fetchMovies(args.data)
        }
    }

    /**
     * Observe the movies, show in listing when returned
     */
    private fun observeMovies() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {
                    initAdapter(response.data ?: emptyList())
                }

                is Resource.Error -> showMessageDialog(requireContext(), response.message!!)
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility =
                        if (response.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }
}