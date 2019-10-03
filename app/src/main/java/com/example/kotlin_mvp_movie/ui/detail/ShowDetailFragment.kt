package com.example.kotlin_mvp_movie.ui.detail


import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_mvp_movie.BaseFragment
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.R.dimen
import com.example.kotlin_mvp_movie.R.layout
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.nineoldandroids.view.ViewHelper
import kotlinx.android.synthetic.main.fragment_show_detail.*
import kotlin.math.max


class ShowDetailFragment : BaseFragment(), ObservableScrollViewCallbacks {

    companion object {
        fun newInstance(item: Item, dailyBoxOfficeList: DailyBoxOfficeList): ShowDetailFragment {
            val args = Bundle()
            args.putSerializable("dailyInfo", dailyBoxOfficeList)
            args.putSerializable("item", item)
            val fragment = ShowDetailFragment()
            fragment.arguments = args
            return fragment
        }
        private const val MAX_TEXT_SCALE_DELTA = 0.3f
    }

    private lateinit var mImageView: View
    private lateinit var mOverlayView: View
    private lateinit var mRecyclerViewBackground: View
    private lateinit var mTitleView: TextView
    private var mActionBarSize: Int = 0
    private var mFlexibleSpaceImageHeight: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("new instance",""+arguments?.getSerializable("item"))

        mFlexibleSpaceImageHeight = resources.getDimensionPixelSize(dimen.flexible_space_image_height)

        mImageView = view.findViewById(R.id.image)
        mOverlayView = view.findViewById(R.id.overlay)
        mTitleView = view.findViewById(R.id.title)
        mRecyclerViewBackground = view.findViewById(R.id.list_background)

        recycler.setScrollViewCallbacks(this)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(false)

        val headerView = LayoutInflater.from(context).inflate(layout.recycler_header, null)
        headerView.post { headerView.layoutParams.height = mFlexibleSpaceImageHeight }

        setDummyData(recycler)

        val scale = 1 + MAX_TEXT_SCALE_DELTA
        list_background.post { ViewHelper.setTranslationY(mRecyclerViewBackground,
            mFlexibleSpaceImageHeight.toFloat()
        ) }

        mTitleView.text = "Test"

        ViewHelper.setTranslationY(mOverlayView, mFlexibleSpaceImageHeight.toFloat())
        title.post {
            ViewHelper.setTranslationY(title, mFlexibleSpaceImageHeight - title.height * scale)
            ViewHelper.setPivotX(title, 0F)
            ViewHelper.setPivotY(title, 0F)
            ViewHelper.setScaleX(title, scale)
            ViewHelper.setScaleY(title, scale)
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
        ViewHelper.setPivotY(mTitleView, 0F)
        ViewHelper.setScaleX(mTitleView, scale)
        ViewHelper.setScaleY(mTitleView, scale)

        // Translate title text
        val maxTitleTranslationY =
            (mFlexibleSpaceImageHeight - mTitleView!!.height * scale).toInt()
        val titleTranslationY = maxTitleTranslationY - scrollY
        ViewHelper.setTranslationY(mTitleView, titleTranslationY.toFloat())

    }

    override fun onDownMotionEvent() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun setPivotXToTitle() {
        val config = resources.configuration
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT && config.layoutDirection === View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView,
                requireActivity().findViewById<View>(android.R.id.content).width.toFloat()
            )
        } else {
            ViewHelper.setPivotX(mTitleView, 0f)
        }
    }


}
