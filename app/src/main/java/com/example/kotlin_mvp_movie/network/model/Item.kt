package com.example.kotlin_mvp_movie.network.model

import java.io.Serializable

data class Item(var image: String, var director: String, var actor: String, var userRating: String, var link: String): Serializable