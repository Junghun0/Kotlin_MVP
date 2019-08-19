package com.example.kotlin_mvp_movie.ui.dialog

import com.example.kotlin_mvp_movie.BasePresenter
import com.example.kotlin_mvp_movie.BaseView

interface DialogContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

    }
}