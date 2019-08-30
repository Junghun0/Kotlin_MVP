package com.example.kotlin_mvp_movie.ui.main

import android.util.Log
import com.example.kotlin_mvp_movie.network.MovieApi
import com.example.kotlin_mvp_movie.network.MovieDetailApi
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.network.model.MovieDetail
import com.example.kotlin_mvp_movie.network.model.ServerResponse
import com.example.kotlin_mvp_movie.repository.MovieDetailRepository
import com.example.kotlin_mvp_movie.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private var view: MainContract.View) : MainContract.Presenter, AnkoLogger, CoroutineScope by CoroutineScope(
    Dispatchers.IO){

    private lateinit var movieRepository: MovieApi
    private lateinit var movieDetailRepository: MovieDetailApi
    private lateinit var movieDetailList: ArrayList<Item>

    override fun start() {
        movieRepository = MovieRepository.getInstance()
        movieDetailRepository = MovieDetailRepository.getInstance()
    }

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieNames: List<String>) {
        movieDetailList = ArrayList()
        launch{
            try {
                for (movieName in movieNames){
                    val call : Call<MovieDetail> = movieDetailRepository.getMovieDetails(movieName)
                    val result : MovieDetail? = call.execute().body()
                    movieDetailList.add(result!!.items[0])
                }
            } catch (e: Throwable) {
                Log.e("Error!","${e.message}")
            }
            launch(Dispatchers.Main) {
                view.bindMovieDetails(movieDetailList)
                view.progressStop()
            }
        }
    }

    override fun clearData() {
        movieDetailList.clear()
    }

    override fun getMovieInfo(targetDt: String) {
        movieRepository.getMovieInfo("f8528e508b93d59e755310d63eb0455a", targetDt)
            .enqueue(object :
                Callback<ServerResponse> {
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    view.showErrorMessage(t.toString())
                }

                override fun onResponse(
                    call: Call<ServerResponse>,
                    response: Response<ServerResponse>
                ) {
                    if (response.isSuccessful) {
                        view.bindMovieData(response.body()!!)
                    }
                }
            })
    }
}