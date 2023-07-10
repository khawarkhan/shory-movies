package com.example.shorymovies.network.model.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Response")
    val Response: String,
    @SerializedName("Search")
    val movies: List<Movie>?,
    @SerializedName("totalResults")
    val totalResults: String
)