package com.example.kotlin_mvp_movie.ui.dialog

import android.content.Context
import com.example.kotlin_mvp_movie.BasePresenter
import com.example.kotlin_mvp_movie.BaseView

interface DialogContract {

    interface View : BaseView<Presenter> {

        fun updateMovies(context: Context, date: String)
    }

    interface Presenter : BasePresenter {

        fun update(date: String)
    }
}