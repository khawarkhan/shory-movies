package com.example.shorymovies.network.model.movies

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Poster")
    val Poster: String,
    @SerializedName("Title")
    val Title: String,
    @SerializedName("Type")
    val Type: String,
    @SerializedName("Year")
    val Year: String,
    @SerializedName("imdbID")
    val imdbID: String
)