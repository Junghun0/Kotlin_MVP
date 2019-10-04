package com.example.kotlin_mvp_movie.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.network.model.ServerResponse
import com.example.kotlin_mvp_movie.ui.adapter.MovieRecyclerAdapter
import com.example.kotlin_mvp_movie.ui.detail.ShowDetailActivity
import com.example.kotlin_mvp_movie.ui.dialog.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), MainContract.View {
    override fun hideWarningView() {
        if (warning_layout.isVisible) {
            warning_layout.visibility = View.GONE
        }
    }

    override fun showWarningView() {
        if (!warning_layout.isVisible) {
            warning_layout.visibility = View.VISIBLE
        }
    }

    override lateinit var presenter: MainContract.Presenter
    private lateinit var recyclerAdapter: MovieRecyclerAdapter
    private var movieNameList = ArrayList<String>()
    private val calendarDialog = DialogFragment()
    private var targetDt = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()
        getCurDate()

        recyclerAdapter = MovieRecyclerAdapter(this) { dailyBoxOfficeList, item ->
            startActivity<ShowDetailActivity>("dailyBoxOffice" to dailyBoxOfficeList, "item" to item)
//            supportFragmentManager.beginTransaction()
//                .add(R.id.main_frame_container, ShowDetailFragment.newInstance(item, dailyBoxOfficeList))
//                .addToBackStack(null)
//                .commit()
        }

        main_recyclerView.apply {
            adapter = recyclerAdapter
            addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        }

        calendarDialog.updateDate.observe(this, androidx.lifecycle.Observer { date ->
            progressShow()
            //선택한 날짜가 현재날짜보다 이후일 경우
            if (date.toInt() > targetDt.toInt()) {
                clearData()
                progressStop()
                toolbar.title = "데이터가 존재하지 않습니다."
                showErrorMessage("데이터가 존재하지 않습니다.")
                showWarningView()
            } else {
                clearData()
                presenter.getMovieInfo(date)
                hideWarningView()
                settingToolBar(date)
                main_frame_container.setBackgroundColor(Color.WHITE)
                toolbar.title =
                    calendarDialog.selectedDate + " " + getString(R.string.toolbar_title)
            }
        })
    }

    override fun settingToolBar(curDateTitle: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        title = curDateTitle + " " + getString(R.string.toolbar_title)
    }

    override fun progressStop() {
        progress_bar.visibility = View.GONE
    }

    override fun progressShow() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun clearData() {
        movieNameList.clear()
        recyclerAdapter.clearData()
        presenter.clearData()
    }

    private fun initPresenter() {
        presenter = MainPresenter(this).apply { start() }
    }

    @SuppressLint("SimpleDateFormat")
    override fun getCurDate() {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val curDateFormat = SimpleDateFormat("yyyyMMdd")

        val calendar: Calendar = GregorianCalendar(Locale.KOREA)
        calendar.add(Calendar.DATE, -1)

        val dateStr = dateFormat.format(calendar.time)
        targetDt = curDateFormat.format(calendar.time)

        presenter.getMovieInfo(targetDt)
        settingToolBar(dateStr)
    }

    override fun bindMovieData(movieData: ServerResponse) {
        extractMovieNames(movieData.boxOfficeResult!!.dailyBoxOfficeList)
        recyclerAdapter.addData(movieData.boxOfficeResult!!.dailyBoxOfficeList)
    }

    override fun bindMovieDetails(movieDetails: ArrayList<Item>) {
        recyclerAdapter.addDetail(movieDetails)
    }

    private fun extractMovieNames(list: List<DailyBoxOfficeList>) {
        for (movieName in list) {
            movieNameList.add(movieName.movieNm)
        }
        presenter.getMovieDetails(movieNameList)
    }

    override fun showErrorMessage(message: String) {
        toast(message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_calendar -> calendarDialog.show(supportFragmentManager, "show")
        }
        return true
    }
}
