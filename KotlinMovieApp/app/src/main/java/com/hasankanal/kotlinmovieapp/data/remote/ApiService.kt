package com.hasankanal.kotlinmovieapp.data.remote

import com.hasankanal.kotlinmovieapp.model.detail.MovieDetailResponse
import com.hasankanal.kotlinmovieapp.model.movie.MovieResponse
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") movieId: Int): Single<MovieDetailResponse>

    @GET("movie/{id}")
    fun getFavoriMovie(@Path("id") movieId : Int) : Single<MovieResult>




}