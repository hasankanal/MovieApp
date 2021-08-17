package com.hasankanal.kotlinmovieapp.ui.main.popular

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hasankanal.kotlinmovieapp.data.remote.ApiClient
import com.hasankanal.kotlinmovieapp.data.remote.FIRST_PAGE
import com.hasankanal.kotlinmovieapp.model.movie.MovieResponse
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PopularViewModel(application: Application) : AndroidViewModel(application) {

    var page = FIRST_PAGE
    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    val popularMovie = MutableLiveData<List<MovieResult>>()
    val loadingPopularMovie = MutableLiveData<Boolean>()

    fun getPopularMovies(){
        loadingPopularMovie.value = true
        disposable.add(
            apiClient.getPopularMovies(FIRST_PAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        popularMovie.value = t.results
                        loadingPopularMovie.value = false
                        Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR FRAGMENT : ", "POPULAR ERROR"+e.message)
                    }

                })
        )
    }

     fun nextpage(){
         loadingPopularMovie.value = true
         disposable.add(
             apiClient.getPopularMovies(page+1)
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                     override fun onSuccess(t: MovieResponse) {
                         popularMovie.value = t.results
                         loadingPopularMovie.value = false
                         Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                     }

                     override fun onError(e: Throwable) {
                         Log.i("POPULAR FRAGMENT : ", "UPCOMING ERROR"+e.message)
                     }

                 })
         )
        page=page+1
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}