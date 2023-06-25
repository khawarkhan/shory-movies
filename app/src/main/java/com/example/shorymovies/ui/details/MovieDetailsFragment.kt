package com.example.shorymovies.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shorymovies.R
import com.example.shorymovies.common.AppDialog
import com.example.shorymovies.databinding.FragmentMovieDetailsBinding
import com.example.shorymovies.network.model.Resource
import com.example.shorymovies.network.model.details.MovieDetails
import com.example.shorymovies.ui.adapters.ChipsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale


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

    val args: MovieDetailsFragmentArgs by navArgs()

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

        context?.let {
            observeMovieDetails()
            fetchMovieDetails()
        }

    }


    /**
     * Fetch movie details for IMDB id
     */
    private fun fetchMovieDetails() {
        lifecycleScope.launch {
            viewModel.fetchMovieDetails(args.data)
        }
    }


    /**
     * Observe the movies, show in listing when returned
     */
    private fun observeMovieDetails() {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner) { response ->

            handleViewsVisibility(View.GONE)
            when (response) {
                is Resource.Success -> {

                    response.data?.let {
                        updateUI(it)

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

    /**
     * Hide/Show views
     */
    private fun handleViewsVisibility(visibility: Int) {
        binding.llDetailHeader.imageViewPoster.visibility = visibility
        binding.llDetailHeader.textViewTitle.visibility = visibility
        binding.llDetailHeader.textViewDirector.visibility = visibility
        binding.llDetailHeader.imageViewVideo.visibility = visibility
        binding.llDetailHeader.textViewTypeDuration.visibility = visibility

        binding.llDetailFooter.textViewOverview.visibility = visibility
        binding.llDetailFooter.imageViewRating.visibility = visibility
        binding.llDetailFooter.textViewRating.visibility = visibility
        binding.llDetailFooter.textViewDescription.visibility = visibility
        binding.llDetailFooter.textViewActorsTitle.visibility = visibility

        binding.llDetailFooter.textViewWritersTitle.visibility = visibility
        binding.llDetailFooter.llDivider.visibility = visibility
        binding.llDetailFooter.llDivider2.visibility = visibility
    }

    /**
     * update UI with small stuff
     */
    private fun updateUI(movie: MovieDetails) {

        /** broken further in small portion of code for better understanding */
        updateUIImageTitleRatingDesc(movie)
        updateUIActorsWriterDirectors(movie)
        updateUITimeTypeDuration(movie)
        setUpVideoPlayer(movie.videoUrl)
    }


    /**
     * update movie poster, title, rating and description
     */
    private fun updateUIImageTitleRatingDesc(movie: MovieDetails){
        /** show hidden views */
        handleViewsVisibility(View.VISIBLE)

        // set post image
        Glide.with(binding.root.context).load(movie.Poster)
            .placeholder(R.drawable.placeholder_square)
            .circleCrop()
            .into(binding.llDetailHeader.imageViewPoster)

        // rating
        binding.llDetailFooter.textViewRating.text = movie.imdbRating

        // movie plot
        binding.llDetailFooter.textViewDescription.text = movie.Plot

        // movie title
        binding.llDetailHeader.textViewTitle.text = movie.Title
    }

    /**
     * update movie actors, writers and directors list
     */
    private fun updateUIActorsWriterDirectors(movie: MovieDetails){
        // show actors
        movie.actorsList?.let { actors ->
            setActors(actors)
        }


        // show writters
        movie.writerList?.let { actors ->
            setWriters(actors)
        }


        // directed by
        String.format(
            binding.root.context.getString(R.string.direction_by),
            movie.Director
        ).let {
            binding.llDetailHeader.textViewDirector.text = it
        }
    }

    private fun updateUITimeTypeDuration(movie: MovieDetails){
        // duration, rated, type, year
        val type = movie.Type.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        String.format(
            binding.root.context.getString(R.string.type_year_rated_duration),
            type,
            movie.Year,
            movie.Rated,
            movie.Runtime,
        ).let {
            binding.llDetailHeader.textViewTypeDuration.text = it
        }

    }


    /**
     * display actors in listing
     */
    private fun setActors(actors: List<String>) {
        binding.llDetailFooter.actorsRecyclerView.adapter = ChipsAdapter(actors)
    }

    /**
     * display writers in listing
     */
    private fun setWriters(actors: List<String>) {
        binding.llDetailFooter.writersRecyclerView.adapter = ChipsAdapter(actors)
    }


    /**
     * set up video player to watch movie
     *
     * ALERT!! omdb API has no movie url so just to complete the feature,
     * I've added bunny video url in MovieDetailUseCase
     */
    private fun setUpVideoPlayer(url: String) {

        binding.llDetailHeader.imageViewVideo.setVideoURI(Uri.parse(url))

        val mediaController = MediaController(requireContext())

        mediaController.setAnchorView(binding.llDetailHeader.imageViewVideo)
        mediaController.setMediaPlayer(binding.llDetailHeader.imageViewVideo)

        /** assining media controller to VideoView */
        binding.llDetailHeader.imageViewVideo.setMediaController(mediaController)
        binding.llDetailHeader.imageViewVideo.start()

        binding.llDetailHeader.imageViewVideo.setOnCompletionListener {
            AppDialog.showAlertDialog(
                requireContext(),
                null,
                getString(R.string.movie_finshed),
                { _, _ ->
                    findNavController().popBackStack()
                },
            )
        }
    }


    override fun onPause() {
        binding.llDetailHeader.imageViewVideo.stopPlayback()
        super.onPause()
    }

    override fun onResume() {
        binding.llDetailHeader.imageViewVideo.resume()
        super.onResume()
    }

    override fun onDestroy() {
        binding.llDetailHeader.imageViewVideo.stopPlayback()
        super.onDestroy()
    }

}