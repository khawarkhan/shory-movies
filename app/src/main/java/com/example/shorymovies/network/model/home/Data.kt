package com.example.shorymovies.network.model.home

import com.google.gson.annotations.SerializedName

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName("results")
    val heroes: List<SuperHero>,
    val total: Int
)