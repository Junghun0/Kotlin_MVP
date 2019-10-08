package com.example.kotlin_mvp_movie.ui.detail

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_mvp_movie.BaseActivity
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item
import com.example.kotlin_mvp_movie.network.model.details.Detail
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.nineoldandroids.view.ViewHelper
import kotlin.math.max

class ShowDetailActivity : BaseActivity() , ObservableScrollViewCallbacks, ShowDetailContract.View {
    override fun bindMovieData(movieInfo: Detail) {
        testList.add(movieInfo)
        setDummyDataWithHeader(recyclerView, mFlexibleSpaceImageHeight, testList, dailyBoxOfficeList, clickedDate, item)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(applicationContext, message , Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val MAX_TEXT_SCALE_DELTA = 0.3f
    }
    private var testList = ArrayList<Detail>()

    override lateinit var presenter: ShowDetailContract.Presenter
    private lateinit var mImageView: View
    private lateinit var mOverlayView: View
    private lateinit var mRecyclerViewBackground: View
    private lateinit var mTitleView: TextView
    private var mActionBarSize = 0
    private var mFlexibleSpaceImageHeight = 0
    private lateinit var recyclerView: ObservableRecyclerView
    private lateinit var headerView: View

    private lateinit var dailyBoxOfficeList: DailyBoxOfficeList
    private lateinit var item: Item
    private lateinit var clickedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        initPresenter()

        dailyBoxOfficeList = intent.getSerializableExtra("dailyBoxOffice") as DailyBoxOfficeList
        val openYear = dailyBoxOfficeList.openDt.split("-")
        clickedDate = intent.getStringExtra("clickedDate") as String
        item = intent.getSerializableExtra("item") as Item

        presenter.getMovieInfo(dailyBoxOfficeList.movieNm , openYear[0].toInt())

        mFlexibleSpaceImageHeight = resources.getDimensionPixelSize(R.dimen.flexible_space_image_height)
        mActionBarSize = actionBarSize

        recyclerView = findViewById(R.id.recycler)
        recyclerView.setScrollViewCallbacks(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)
        headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, null)

        headerView.post { headerView.layoutParams.height = mFlexibleSpaceImageHeight }

        mImageView = findViewById(R.id.image)
        mOverlayView = findViewById(R.id.overlay)
        mTitleView = findViewById(R.id.title)
        mTitleView.text = dailyBoxOfficeList.movieNm

        mRecyclerViewBackground = findViewById(R.id.list_background)

        val scale: Float = 1 + MAX_TEXT_SCALE_DELTA
        mRecyclerViewBackground.post {
            ViewHelper.setTranslationY(mRecyclerViewBackground, mFlexibleSpaceImageHeight.toFloat())
        }

        ViewHelper.setTranslationY(mOverlayView, mFlexibleSpaceImageHeight.toFloat())
        mTitleView.post {
            ViewHelper.setTranslationY(mTitleView, (mFlexibleSpaceImageHeight - mTitleView.height * scale))
            ViewHelper.setPivotX(mTitleView, 0F)
            ViewHelper.setPivotY(mTitleView, 0F)
            ViewHelper.setScaleX(mTitleView, scale)
            ViewHelper.setScaleY(mTitleView, scale)
        }
    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        val flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize
        val minOverlayTransitionY = mActionBarSize - mOverlayView.height

        ViewHelper.setTranslationY(
            mOverlayView,
            ScrollUtils.getFloat((-scrollY).toFloat(), minOverlayTransitionY.toFloat(), 0f)
        )
        ViewHelper.setTranslationY(
            mImageView,
            ScrollUtils.getFloat((-scrollY / 2).toFloat(), minOverlayTransitionY.toFloat(), 0f)
        )

        // Translate list background
        ViewHelper.setTranslationY(
            mRecyclerViewBackground,
            max(0, -scrollY + mFlexibleSpaceImageHeight).toFloat()
        )

        // Change alpha of overlay
        ViewHelper.setAlpha(
            mOverlayView,
            ScrollUtils.getFloat(scrollY.toFloat() / flexibleRange, 0f, 1f)
        )

        // Scale title text
        val scale = 1 + ScrollUtils.getFloat(
            ((flexibleRange - scrollY) / flexibleRange).toFloat(),
            0f,
            MAX_TEXT_SCALE_DELTA
        )
        setPivotXToTitle()
        ViewHelper.setPivotY(mTitleView, 0f)
        ViewHelper.setScaleX(mTitleView, scale)
        ViewHelper.setScaleY(mTitleView, scale)

        // Translate title text
        val maxTitleTranslationY = (mFlexibleSpaceImageHeight - mTitleView.height * scale).toInt()
        val titleTranslationY = maxTitleTranslationY - scrollY
        ViewHelper.setTranslationY(mTitleView, titleTranslationY.toFloat())
    }

    override fun onDownMotionEvent() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun setPivotXToTitle() {
        val config = resources.configuration
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT && config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(
                mTitleView,
                findViewById<View>(android.R.id.content).width.toFloat()
            )
        } else {
            ViewHelper.setPivotX(mTitleView, 0f)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun initPresenter() {
        presenter = ShowDetailPresenter(this).apply { start() }
    }


}
