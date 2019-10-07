package com.example.kotlin_mvp_movie.network

import com.example.kotlin_mvp_movie.network.model.details.Detail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoApi {
    // http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json.jsp?collection=kmdb_new&ServiceKey=30ELK99F96KDF2MYO050
    //title=조커&createDts=2019
    @GET("search_json.jsp?collection=kmdb_new&ServiceKey=30ELK99F96KDF2MYO050")
    fun getMovieInfos(@Query("title") movieName: String, @Query("releaseDts") createDts: Int): Call<Detail>
}