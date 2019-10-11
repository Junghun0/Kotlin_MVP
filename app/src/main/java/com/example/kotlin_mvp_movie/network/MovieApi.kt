package com.example.kotlin_mvp_movie.network

import com.example.kotlin_mvp_movie.network.model.ServerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    @GET("searchDailyBoxOfficeList.json")
    fun getMovieInfo(@Query("key") key: String, @Query("targetDt") targetDt: String): Call<ServerResponse>
}