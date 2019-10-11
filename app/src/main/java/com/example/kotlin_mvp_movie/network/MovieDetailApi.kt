package com.example.kotlin_mvp_movie.network

import com.example.kotlin_mvp_movie.network.model.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieDetailApi {
    @Headers("X-Naver-Client-Id: nqyQmCYpD9MCWhuGJ5xJ", "X-Naver-Client-Secret: 3iwxzdBiSz")
    @GET("movie.json")
    fun getMovieDetails(@Query("query") movieName: String): Call<MovieDetail>
}