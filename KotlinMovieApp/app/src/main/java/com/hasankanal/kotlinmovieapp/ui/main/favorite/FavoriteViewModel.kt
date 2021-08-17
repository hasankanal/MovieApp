package com.hasankanal.kotlinmovieapp.ui.main.favorite

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hasankanal.kotlinmovieapp.data.firebase.FirebaseSource
import com.hasankanal.kotlinmovieapp.data.remote.ApiClient
import com.hasankanal.kotlinmovieapp.repositories.UserRepository
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val firebaseAuth = FirebaseSource()
    private val userRepository = UserRepository(firebaseAuth)
    private val dbReference = FirebaseDatabase.getInstance().reference.child("favori")

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    val favMovie = MutableLiveData<List<MovieResult>>()
    var movieList = mutableListOf<MovieResult>()
    val loadingFavoriMovie = MutableLiveData<Boolean>()

    var idList = ArrayList<Int>()




    val user by lazy {
        userRepository.currentUser()
    }



    fun addValueEventListener() {

        dbReference.child(getUsernameFromEmail(user!!.email)).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snp : DataSnapshot in snapshot.children){
                    for(snp2 : DataSnapshot in snp.children){
                        if(idList.contains(snp2.value.hashCode())){
                            System.out.println("Film ID'si zaten var!!")
                        }else{
                            idList.add(snp2.value.hashCode())
                        }
                    }
                 }
                System.out.println(idList)
                  for(a in idList.iterator()){
                    getFavoriteMovies(a)
                  }
                favMovie.value = movieList
                System.out.println("Silindi")
            }

            override fun onCancelled(error: DatabaseError) {
                System.out.println("HATA")
            }

        })
    }


        fun getFavoriteMovies(id: Int) {
            loadingFavoriMovie.value = true
            disposable.add(
                apiClient.getFavMovies(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<MovieResult>() {
                        override fun onSuccess(t: MovieResult) {
                            movieList.add(t)
                            System.out.println("Sonuc : " + t)
                            loadingFavoriMovie.value = false
                            System.out.println("MovieList: " + movieList)
                        }

                        override fun onError(e: Throwable) {
                            System.out.println("FavoriteViewModel getFavoriteMovie ERROR")
                        }

                    })
            )
        }

    private fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    fun getFavBt(view : View){
        addValueEventListener()


    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}