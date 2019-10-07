package com.example.kotlin_mvp_movie.ui.detail

import com.example.kotlin_mvp_movie.BasePresenter
import com.example.kotlin_mvp_movie.BaseView
import com.example.kotlin_mvp_movie.network.model.details.Detail

interface ShowDetailContract {
    interface View : BaseView<Presenter> {

        fun bindMovieData(movieInfo: Detail)

        fun showErrorMessage(message: String)

    }

    interface Presenter : BasePresenter {

        fun attachView(view: View)

        fun getMovieInfo(movieName: String, createDts: Int)
    }
}