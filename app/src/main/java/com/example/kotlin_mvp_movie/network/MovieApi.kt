package com.example.kotlin_mvp_movie.network

import com.example.kotlin_mvp_movie.network.model.ServerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    //key=f8528e508b93d59e755310d63eb0455a
    //targetDt = &targetDt=20190427
    @GET("searchDailyBoxOfficeList.json")
    fun getMovieInfo(@Query("key") key: String, @Query("targetDt") targetDt: String): Call<ServerResponse>
}