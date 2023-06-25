package com.example.shorymovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shorymovies.R
import com.example.shorymovies.databinding.ItemMovieListBinding
import com.example.shorymovies.network.model.movies.Movie
import java.util.Locale


/**
 * Movies adapter
 */
/**
 * created by Khawar Habib on 25/06/2023
 *
 * Movie adapter: adapts list of movie's details in visuals (Listview)
 * *
 **/
class MoviesAdapter(var movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(movies[position])


    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieListBinding.bind(view)


        fun bind(movie: Movie) {
            binding.textViewName.text = movie.Title

            val year = String.format(
                binding.root.context.getString(R.string.release_date),
                movie.Year
            )
            binding.textViewDate.text = year

            binding.textViewType.text = movie.Type.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }


            if (movie.Poster.isNotEmpty())
                Glide.with(binding.root.context).load(movie.Poster)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                    .into(binding.imageView)
            else
                Glide.with(binding.root.context).load(R.drawable.ic_image_placeholder)
                    .into(binding.imageView)

            // change movie listing background dynamically
//            binding.root.background = Utils.getRandomShape()
        }
    }
}