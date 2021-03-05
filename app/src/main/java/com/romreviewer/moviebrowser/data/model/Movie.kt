package com.romreviewer.moviebrowser.data.model


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val overview: String,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val vote_count: Int
    )