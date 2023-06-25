package com.example.shorymovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shorymovies.R
import com.example.shorymovies.databinding.HeroCellBinding
import com.example.shorymovies.network.model.home.SuperHero


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Heroes adapter: adapts list of super hero characters in visuals (Listview)
 * *
 **/
class HeroesAdapter(var movies: List<SuperHero>) :
    RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hero_cell, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) =
        holder.bind(movies[position])


    inner class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HeroCellBinding.bind(view)


        fun bind(hero: SuperHero) {
            binding.textViewTitle.text = hero.name

            val thumbnail: String = hero.thumbnail.let {
                "${it.path}.${it.extension}"
            }

            if (thumbnail.isNotEmpty())
                Glide.with(binding.root.context).load(thumbnail)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(2)))
                    .into(binding.imageView)
            else
                Glide.with(binding.root.context).load(R.drawable.ic_image_placeholder)
                    .into(binding.imageView)

        }
    }
}