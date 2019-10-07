package com.example.kotlin_mvp_movie.network.model.details

data class Result(
    var dOCID: String,
    var movieId: String,
    var movieSeq: String,
    var title: String,
    var titleEng: String,
    var titleOrg: String,
    var titleEtc: String,
    var prodYear: String,
    var director: ArrayList<Director>,
    var actor: ArrayList<Actor>,
    var nation: String,
    var company: String,
    var plot: String,
    var runtime: String,
    var Rating: ArrayList<Rating>,
    var genre: String,
    var kmdbbUrl: String,
    var type: String,
    var keywords: String,
    var posters: String,
    var stlls: String,
    var staff: ArrayList<Staff>,
    var vod: ArrayList<Vod>
)
