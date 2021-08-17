package com.hasankanal.kotlinmovieapp.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    val page: Int,
    @SerializedName("results")
    var results: List<MovieResult>,

)