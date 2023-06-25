package com.example.shorymovies.network.model.home

import com.google.gson.annotations.SerializedName

data class SuperHero(

    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    @SerializedName("name")
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    val urls: List<Url>
)