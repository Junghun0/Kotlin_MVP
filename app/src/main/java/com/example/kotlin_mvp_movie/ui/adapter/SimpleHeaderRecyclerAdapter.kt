package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.network.model.details.Detail
import com.example.kotlin_mvp_movie.ui.webview.WebViewActivity
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


class SimpleHeaderRecyclerAdapter(
    val context: Context,
    private val mItems: ArrayList<Detail>,
    private val mHeaderView: View?,
    private val mData: DailyBoxOfficeList,
    private val clicked: String,
    private val mData2: Item
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemCount(): Int {
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
                LayoutInflater.from(parent.context).inflate(R.layout.detail_cardview_layout, parent, false)
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

            val splitDate = mData.openDt.split("-")
            val openDT = splitDate[0]+splitDate[1]+splitDate[2]
            if (clicked.isEmpty()){
                viewHolder.openingDate.text = getDateDistance(openDT) + " 일째"
            }else{
                viewHolder.openingDate.text = getDateDistance(openDT , clicked) + " 일째"
            }

            val stllUrl = mItems[position - 1].Data[0].Result[0].stlls.split("|")
            val imageList = ArrayList<String>()
            imageList.addAll(stllUrl)
            viewHolder.galleryViewPager.adapter = GalleryViewPagerAdapter(imageList, context)
            viewHolder.totalAudi.text = (mData.audiAcc.toInt() / 10000).toString() + " 만명"
            viewHolder.actorRecyclerView.adapter = ActorRecyclerViewAdapter(mItems[position - 1].Data[0].Result[0].staff, context)
            viewHolder.actorRecyclerView.isNestedScrollingEnabled = false
            viewHolder.webViewLink.text = mData2.link
            viewHolder.webViewLink.setOnClickListener {
                context.startActivity<WebViewActivity>("url" to mData2.link)
            }
        }
    }

    internal class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var keyWordTextView: TextView = view.findViewById(R.id.detail_keyword)
        var plotTextView: TextView = view.findViewById(R.id.moviePlot)
        var directorName: TextView = view.findViewById(R.id.movieDirector)
        var moviePlayTime: TextView = view.findViewById(R.id.moviePlayTime)
        var movieGenre: TextView = view.findViewById(R.id.movieGenre)
        var movieCountry: TextView = view.findViewById(R.id.movieCountry)
        var movieYear: TextView = view.findViewById(R.id.movieMakeYear)
        var movieTitle: TextView = view.findViewById(R.id.movieTitle)
        var galleryViewPager: ViewPager2 = view.findViewById(R.id.gallery_viewpager)
        val totalAudi: TextView = view.findViewById(R.id.totalAudi)
        val openingDate: TextView = view.findViewById(R.id.detail_openDate)
        val actorRecyclerView: RecyclerView = view.findViewById(R.id.actor_recyclerView)
        val webViewLink: TextView = view.findViewById(R.id.webView_Link)
    }

    private fun getDateDistance(end: String): String{
        // 선택날짜 - 개봉날짜
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.KOREA)

        val calendar: Calendar = GregorianCalendar(Locale.KOREA)
        calendar.add(Calendar.DATE, -1)

        val dateStart = formatter.format(calendar.time).toString()

        val beginDate = formatter.parse(dateStart)
        val endDate = formatter.parse(end)

        val diff = beginDate.time - endDate.time
        val diffDays = diff / (24 * 60 * 60 * 1000)
        return diffDays.toString()
    }

    private fun getDateDistance(start: String, end: String): String{
        // start - 개봉일 , end - 선택일
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val startDate = formatter.parse(start)
        val endDate = formatter.parse(end)

        val diff = endDate.time - startDate.time
        val diffDays = diff / (24 * 60 * 60 * 1000)

        return diffDays.toString()
    }
}