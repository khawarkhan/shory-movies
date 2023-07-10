package com.example.shorymovies.network.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies_table")
data class Movie(

    /** imdbID would be the primary key */
    @PrimaryKey(autoGenerate = false)
    @SerializedName("imdbID")
    val imdbID: String,


    @SerializedName("Poster")
    val Poster: String,
    @SerializedName("Title")
    val Title: String,
    @SerializedName("Type")
    val Type: String,
    @SerializedName("Year")
    val Year: String,

    /**
     * key for for db used as foreign key (just to keep movie against it)
     */
    @SerializedName("MovieName")
    var movieName: String?,
)