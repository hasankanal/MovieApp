package com.hasankanal.kotlinmovieapp.ui.main.upcoming

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

class UpcomingViewModel(application: Application) : AndroidViewModel(application) {

    var page = FIRST_PAGE

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    val upcomingMovie = MutableLiveData<List<MovieResult>>()
    val loadingUpcomingMovie = MutableLiveData<Boolean>()

    fun getUpcomingMovies(){
        loadingUpcomingMovie.value = true
        disposable.add(
            apiClient.getUpcomingMovies(FIRST_PAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        upcomingMovie.value = t.results
                        loadingUpcomingMovie.value = false
                        Log.i("HOME FRAGMENT : ", "UPCOMING WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("HOME FRAGMENT : ", "UPCOMING ERROR"+e.message)
                    }

                })
        )
    }

    fun nextPageUpcoming(){
        loadingUpcomingMovie.value = true
        disposable.add(
            apiClient.getUpcomingMovies(page+1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        upcomingMovie.value = t.results
                        loadingUpcomingMovie.value = false
                        Log.i("HOME FRAGMENT : ", "UPCOMING WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("HOME FRAGMENT : ", "UPCOMING ERROR"+e.message)
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