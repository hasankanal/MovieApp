package com.hasankanal.kotlinmovieapp.model.user

import com.google.firebase.database.IgnoreExtraProperties
import com.hasankanal.kotlinmovieapp.model.favori.FavMovie

@IgnoreExtraProperties
data class User (
        var favMovieList : ArrayList<FavMovie>,
        var userKey:String ="",
        var name:String  ="",
        var email:String =""

)