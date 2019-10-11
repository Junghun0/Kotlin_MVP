package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item


class MovieRecyclerAdapter(
    private val context: Context,
    private val clickListener: (DailyBoxOfficeList, Item) -> Unit
) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    private var mMovieDataList = ArrayList<DailyBoxOfficeList>()
    private var mMovieDetailList = ArrayList<Item>()

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val audiPercent: TextView = itemView.findViewById(R.id.audi_percent)
        val audiPercentImage: ImageView = itemView.findViewById(R.id.audiPercent_image)
        val movieLayout: ConstraintLayout = itemView.findViewById(R.id.movie_layout)
        val movieThumbnail: ImageView = itemView.findViewById(R.id.movie_thumbNail)
        val movieRateText: TextView = itemView.findViewById(R.id.movie_rating_text)
        val movieRank: TextView = itemView.findViewById(R.id.movie_rank_textView)
        val movieOpendt: TextView = itemView.findViewById(R.id.movie_openDt)
        val movieName: TextView = itemView.findViewById(R.id.movie_name)
    }

    fun clearData() {
        mMovieDetailList.clear()
        mMovieDataList.clear()
        notifyDataSetChanged()
    }

    fun addData(movieDataList: ArrayList<DailyBoxOfficeList>) {
        mMovieDataList.addAll(movieDataList)
    }

    fun addDetail(movieDetailList: ArrayList<Item>) {
        mMovieDetailList.addAll(movieDetailList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = mMovieDataList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieDetails: Item
        val movieData: DailyBoxOfficeList
        if (mMovieDetailList.size == 10) {
            movieDetails = mMovieDetailList[position]
            movieData = mMovieDataList[position]
            holder.movieLayout.setOnClickListener {
                clickListener.invoke(movieData, movieDetails)
            }
            holder.movieName.text = movieData.movieNm
            holder.movieOpendt.text = makeDateFormat(movieData.openDt)
            holder.audiPercent.text = movieData.audiChange + "%"
            checkAudiPercent(holder, movieData.audiChange)
            holder.movieRateText.text = movieDetails.userRating
            holder.movieRank.text = movieData.rnum
            holder.movieThumbnail.clipToOutline = true
            Glide.with(context)
                .load(movieDetails.image)
                .into(holder.movieThumbnail)
        }
    }

    private fun checkAudiPercent(holder: MovieViewHolder, percent: String) {
        if (percent.first() == '-'){
            holder.audiPercentImage.setImageResource(R.drawable.up)
        }else{
            holder.audiPercentImage.setImageResource(R.drawable.down)
        }
    }

    private fun makeDateFormat(curDate: String): String {
        val strBuilder = StringBuilder()
        val dateFormatList: ArrayList<String> = curDate.split("-") as ArrayList<String>
        strBuilder.append(dateFormatList[0])
        strBuilder.append("년 ")
        if (dateFormatList[1].first() == '0') {
            strBuilder.append(dateFormatList[1].last())
        } else {
            strBuilder.append(dateFormatList[1])
        }
        strBuilder.append("월 ")
        if (dateFormatList[2].first() == '0') {
            strBuilder.append(dateFormatList[2].last())
        } else {
            strBuilder.append(dateFormatList[2])
        }
        strBuilder.append("일")
        return strBuilder.toString()
    }

}