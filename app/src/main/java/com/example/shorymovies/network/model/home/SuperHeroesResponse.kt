package com.example.shorymovies.network.model.home

import com.example.shorymovies.network.model.home.Data
import com.google.gson.annotations.SerializedName

data class SuperHeroesResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,

    @SerializedName("data")
    val `data`: Data,
    val etag: String,
    val status: String
)