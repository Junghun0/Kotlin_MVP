package com.example.kotlin_mvp_movie.repository

import com.example.kotlin_mvp_movie.network.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {
    private const val BASE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"

    private lateinit var movieApiImpl: MovieApi

    init {
        buildRetrofit()
    }

    fun getInstance(): MovieApi {
        return movieApiImpl
    }

    private fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieApiImpl = retrofit.create(MovieApi::class.java)
    }
}