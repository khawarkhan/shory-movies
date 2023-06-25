package com.example.shorymovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shorymovies.R
import com.example.shorymovies.databinding.LayoutRatingsBinding


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Chips adapter: adapts list of String into a recyclerview listing, in shape of chipd
 *
 * for simply displays like showing list of actors/writers/ratings (Listview)
 *
 **/
class ChipsAdapter(var chips: List<String>) :
    RecyclerView.Adapter<ChipsAdapter.RatingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingsViewHolder {
        return RatingsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_ratings, parent, false)
        )
    }

    override fun getItemCount(): Int = chips.size

    override fun onBindViewHolder(holder: RatingsViewHolder, position: Int) {
        holder.bind(chips[position])
    }

    inner class RatingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutRatingsBinding.bind(view)


        fun bind(chip: String) {
            binding.textViewRatings.text = chip

        }
    }
}