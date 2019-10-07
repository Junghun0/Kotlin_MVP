package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.details.Detail
import java.util.*


class SimpleHeaderRecyclerAdapter(
    val context: Context,
    private val mItems: ArrayList<Detail>,
    private val mHeaderView: View?,
    private val mData: DailyBoxOfficeList
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemCount(): Int {
        Log.e("mItems item Count",""+mItems)
        return if (mHeaderView == null) {
            mItems.size
        } else {
            mItems.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            HeaderViewHolder(mHeaderView!!)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(com.example.kotlin_mvp_movie.R.layout.detail_cardview_layout, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.keyWordTextView.text = mItems[position - 1].Data[0].Result[0].keywords
            viewHolder.plotTextView.text = mItems[position - 1].Data[0].Result[0].plot
            viewHolder.directorName.text = mItems[position - 1].Data[0].Result[0].director[0].directorNm
            viewHolder.moviePlayTime.text = mItems[position - 1].Data[0].Result[0].runtime + "분"
            viewHolder.movieGenre.text = mItems[position - 1].Data[0].Result[0].genre
            viewHolder.movieCountry.text = mItems[position - 1].Data[0].Result[0].nation
            viewHolder.movieYear.text = mItems[position - 1].Data[0].Result[0].prodYear
            viewHolder.movieTitle.text = mItems[position - 1].Data[0].Result[0].company

            Log.e("vodUrl-> "," "+mItems[position - 1].Data[0].Result[0].vod[0].vodUrl)
            Log.e("posters-> ",""+mItems[position - 1].Data[0].Result[0].posters)
            Log.e("still-> "," "+mItems[position - 1].Data[0].Result[0].stlls)
            val stllUrl = mItems[position - 1].Data[0].Result[0].stlls.split("|")
            val imageList = ArrayList<String>()
            imageList.addAll(stllUrl)
            viewHolder.galleryViewPager.adapter = GalleryViewPagerAdapter(imageList, context)

            viewHolder.totalAudi.text = (mData.audiAcc.toInt() / 10000).toString() + " 만명"
        }
    }

    internal class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var keyWordTextView: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.detail_keyword)
        var plotTextView: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.moviePlot)
        var directorName: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.movieDirector)
        var moviePlayTime: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.moviePlayTime)
        var movieGenre: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.movieGenre)
        var movieCountry: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.movieCountry)
        var movieYear: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.movieMakeYear)
        var movieTitle: TextView = view.findViewById(com.example.kotlin_mvp_movie.R.id.movieTitle)
        var galleryViewPager: ViewPager2 = view.findViewById(com.example.kotlin_mvp_movie.R.id.gallery_viewpager)
        val totalAudi: TextView = view.findViewById(R.id.totalAudi)
    }
}