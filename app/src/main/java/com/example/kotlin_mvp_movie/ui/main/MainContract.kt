package com.example.kotlin_mvp_movie.ui.main

import com.example.kotlin_mvp_movie.BasePresenter
import com.example.kotlin_mvp_movie.BaseView
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.network.model.ServerResponse

interface MainContract {

    interface View : BaseView<Presenter> {

        fun bindMovieData(movieData: ServerResponse)

        fun showErrorMessage(message: String)

        fun bindMovieDetails(movieDetails: ArrayList<Item>)

        fun getCurDate()

        fun progressStop()

        fun progressShow()

        fun settingToolBar(curDateTitle: String)

        fun clearData()

    }

    interface Presenter : BasePresenter {

        fun attachView(view: View)

        fun getMovieInfo(targetDt: String)

        fun getMovieDetails(movieNames: List<String>)

        fun clearData()

    }
}