package com.example.kotlin_mvp_movie.network.model.details

data class Data(
    var CollName: String,
    var TotalCount: Int,
    var Count: Int,
    var Result: ArrayList<Result>
)