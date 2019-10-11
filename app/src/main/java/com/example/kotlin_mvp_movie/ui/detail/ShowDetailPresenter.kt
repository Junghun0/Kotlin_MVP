package com.example.kotlin_mvp_movie.ui.detail

import com.example.kotlin_mvp_movie.network.MovieInfoApi
import com.example.kotlin_mvp_movie.network.model.details.Detail
import com.example.kotlin_mvp_movie.retrofit.MovieInfoRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailPresenter (private var view: ShowDetailContract.View) : ShowDetailContract.Presenter {
    private lateinit var movieInfoRepository: MovieInfoApi

    override fun start() {
        movieInfoRepository = MovieInfoRetrofit.getInstance()
    }

    override fun attachView(view: ShowDetailContract.View) {
        this.view = view
    }

    override fun getMovieInfo(movieName: String, createDts: Int) {
        movieInfoRepository.getMovieInfos(movieName = movieName, createDts = createDts).enqueue(object: Callback<Detail>{
            override fun onFailure(call: Call<Detail>, t: Throwable) {
                view.showErrorMessage(t.toString())
            }

            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                if (response.isSuccessful){
                    view.bindMovieData(response.body()!!)
                }
            }
        })
    }

}