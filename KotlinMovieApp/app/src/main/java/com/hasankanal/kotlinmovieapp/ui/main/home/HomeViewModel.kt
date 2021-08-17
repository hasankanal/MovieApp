package com.hasankanal.kotlinmovieapp.ui.main.home

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.hasankanal.kotlinmovieapp.data.firebase.FirebaseSource
import com.hasankanal.kotlinmovieapp.data.remote.ApiClient
import com.hasankanal.kotlinmovieapp.repositories.UserRepository
import com.hasankanal.kotlinmovieapp.model.detail.MovieDetailResponse
import com.hasankanal.kotlinmovieapp.model.favori.FavMovie
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class HomeViewModel (application: Application) : AndroidViewModel(application){


    private val firebaseAuth = FirebaseSource()
    private val userRepository = UserRepository(firebaseAuth)


   val user by lazy {
        userRepository.currentUser()
    }


    fun logout(view: View){
        userRepository.logout()
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        Navigation.findNavController(view).navigate(action)
    }
    fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }


    fun getCurrentUserName(): String {
     var name =  getUsernameFromEmail(user!!.email)

        return name
    }


}