package com.hasankanal.kotlinmovieapp.model.favori

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class FavMovie {
    var movieId : Int = 0

    constructor(){

    }

    constructor(
            movieId: Int = 0
            ){

        this.movieId = movieId
    }
}