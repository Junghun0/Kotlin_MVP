package com.example.kotlin_mvp_movie.network.model

data class BoxOfficeResult(
    var boxofficeType: String,
    var showRange: String,
    var dailyBoxOfficeList: ArrayList<DailyBoxOfficeList>
)