package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.ui.detail.WebViewActivity
import org.jetbrains.anko.startActivity


class MovieRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    private var mMovieDataList = ArrayList<DailyBoxOfficeList>()
    private var mMovieDetailList = ArrayList<Item>()

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val movie_thumbNail: ImageView = itemView.findViewById(R.id.movie_thumbNail)
        val movie_rate_text: TextView = itemView.findViewById(R.id.movie_rating_text)
        val movie_rate: RatingBar = itemView.findViewById(R.id.movie_rate)
        val movie_director: TextView = itemView.findViewById(R.id.movie_director)
        val movie_actor: TextView = itemView.findViewById(R.id.movie_actor)
        val movie_openDt: TextView = itemView.findViewById(R.id.movie_openDt)
        val movie_today_audi: TextView = itemView.findViewById(R.id.movie_today_audi)
        val movie_total_audi: TextView = itemView.findViewById(R.id.movie_total_audi)
        val movie_total_sales: TextView = itemView.findViewById(R.id.movie_total_sales)
        val movie_name: TextView = itemView.findViewById(R.id.movie_name)
        val movie_audi_change: TextView = itemView.findViewById(R.id.movie_audi_change)
        val movie_link: TextView = itemView.findViewById(R.id.movie_link)
    }

    fun clearData(){
        mMovieDetailList.clear()
        mMovieDataList.clear()
        notifyDataSetChanged()
    }

    fun addData(movieDataList: ArrayList<DailyBoxOfficeList>){
        mMovieDataList.addAll(movieDataList)
    }

    fun addDetail(movieDetailList: ArrayList<Item>){
        mMovieDetailList.addAll(movieDetailList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = mMovieDataList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieDetails: Item
        val movieData: DailyBoxOfficeList
        if (mMovieDetailList.size == 10){
            movieDetails = mMovieDetailList[position]
            movieData = mMovieDataList[position]

            holder.movie_name.text = movieData.movieNm
            holder.movie_total_sales.text = movieData.salesAcc
            holder.movie_total_audi.text = movieData.audiAcc
            holder.movie_today_audi.text = movieData.audiCnt
            holder.movie_openDt.text = movieData.openDt
            holder.movie_audi_change.text = movieData.audiChange
            holder.movie_rate.rating = movieDetails.userRating.toFloat()/2
            holder.movie_rate_text.text = movieDetails.userRating
            holder.movie_director.text = movieDetails.director
            holder.movie_actor.text = movieDetails.actor
            holder.movie_link.text = movieDetails.link
            Glide.with(context)
                .load(movieDetails.image)
                .into(holder.movie_thumbNail)

            holder.movie_link.setOnClickListener {
                context.startActivity<WebViewActivity>("url" to movieDetails.link)
            }
        }
    }

}