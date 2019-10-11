package com.example.kotlin_mvp_movie.retrofit

import com.example.kotlin_mvp_movie.network.MovieDetailApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDetailRetrofit {
    private const val BASE_URL_MOVIE = "https://openapi.naver.com/v1/search/"

    private lateinit var movieDetailImpl: MovieDetailApi

    init {
        buildRetrofit()
    }

    fun getInstance(): MovieDetailApi {
        return movieDetailImpl
    }

    private fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieDetailImpl = retrofit.create(MovieDetailApi::class.java)
    }
}