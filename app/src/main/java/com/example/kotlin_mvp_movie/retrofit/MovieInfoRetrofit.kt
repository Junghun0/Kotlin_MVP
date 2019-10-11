package com.example.kotlin_mvp_movie.retrofit

import com.example.kotlin_mvp_movie.network.MovieInfoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieInfoRetrofit {
    private const val BASE_URL_MOVIE = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/"

    private lateinit var movieInfoImpl: MovieInfoApi

    init {
        buildRetrofit()
    }

    fun getInstance(): MovieInfoApi {
        return movieInfoImpl
    }

    private fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieInfoImpl = retrofit.create(MovieInfoApi::class.java)
    }
}