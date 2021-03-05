package com.romreviewer.moviebrowser.data.model


import com.google.gson.annotations.SerializedName
import com.romreviewer.moviebrowser.data.model.Movie

data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)