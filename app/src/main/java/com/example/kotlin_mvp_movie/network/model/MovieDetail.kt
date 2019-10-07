package com.example.kotlin_mvp_movie.network.model

data class MovieDetail(
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var items: ArrayList<Item>
)
