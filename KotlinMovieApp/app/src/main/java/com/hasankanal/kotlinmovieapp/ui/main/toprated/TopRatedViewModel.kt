package com.hasankanal.kotlinmovieapp.ui.main.toprated

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

class TopRatedViewModel(application: Application) : AndroidViewModel(application) {

    var page = FIRST_PAGE
    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    val topRatedMovie = MutableLiveData<List<MovieResult>>()
    val loadingTopRatedMovie = MutableLiveData<Boolean>()

    fun getTopRatedMovies(){
        loadingTopRatedMovie.value = true
        disposable.add(
            apiClient.getTopRatedMovies(FIRST_PAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        topRatedMovie.value = t.results
                        loadingTopRatedMovie.value = false
                        Log.i("POPULAR VIEW MODEL", "ÇALIŞTI")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR VIEW MODEL", " "+e.message)
                    }
                })
        )
    }

    fun nextPageTopRated(){
        loadingTopRatedMovie.value = true
        disposable.add(
            apiClient.getTopRatedMovies(page+1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        topRatedMovie.value = t.results
                        loadingTopRatedMovie.value = false
                        Log.i("POPULAR VIEW MODEL", "ÇALIŞTI")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR VIEW MODEL", " "+e.message)
                    }
                })
        )
        page = page+1
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}