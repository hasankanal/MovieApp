package com.hasankanal.kotlinmovieapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hasankanal.kotlinmovieapp.data.remote.ApiClient
import com.hasankanal.kotlinmovieapp.model.detail.MovieDetailResponse
import com.hasankanal.kotlinmovieapp.model.favori.FavMovie
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel (application: Application) : AndroidViewModel(application) {

    private var user: FirebaseUser? = null
    private var mFavMovieReference: DatabaseReference? = null

    private val repository : MovieDetailRepository by lazy {
        MovieDetailRepository(
            application.applicationContext
        )
    }

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    val movieDetails = MutableLiveData<MovieDetailResponse>()
    val loading = MutableLiveData<Boolean>()


    fun insertMovie(movie: MovieResult?) = repository.insertMovie(movie)
    fun deleteMovie(movie: MovieResult?) = repository.deleteMovie(movie)
    fun getSingleMovie(movieId:Int?) : LiveData<MovieResult> = repository.getSingleMovie(movieId)
    fun getAllMovies(): LiveData<List<MovieResult>> = repository.getAllMovies()


    fun getMovieDetails(movieId:Int?){
        loading.value = true
        disposable.add(
            apiClient.getMovieDetails(movieId!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>(){
                    override fun onSuccess(t: MovieDetailResponse) {
                        movieDetails.value = t
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Detail View Model" , "Hata : "+e.message)
                    }

                })
        )
    }


    fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    fun addFavToFirebase(movieId: Int = 0)
    {
        user = FirebaseAuth.getInstance().currentUser
        mFavMovieReference = FirebaseDatabase.getInstance().getReference("favori").child(getUsernameFromEmail(user!!.email))
        mFavMovieReference!!.push().key?.let {
            var favMovie = FavMovie(movieId)
            System.out.println()
            val task = mFavMovieReference!!.child(it).setValue(favMovie)
            task.addOnSuccessListener {
                System.out.println("Task Firebase: BASARILI")
            }.addOnFailureListener{
                System.out.println("Task FirebAse: BASARISIZ" )
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}