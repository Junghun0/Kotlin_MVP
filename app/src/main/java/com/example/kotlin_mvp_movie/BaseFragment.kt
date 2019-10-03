package com.example.kotlin_mvp_movie

import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_mvp_movie.ui.adapter.SimpleRecyclerAdapter
import java.util.*

abstract class BaseFragment : Fragment() {

    private val dummyData = ArrayList<String>()

    protected val actionBarSize: Int
        get() {
            val activity = activity ?: return 0
            val typedValue = TypedValue()
            val textSizeAttr = intArrayOf(R.attr.actionBarSize)
            val indexOfAttrTextSize = 0
            val a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr)
            val actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1)
            a.recycle()
            return actionBarSize
        }

    protected val screenHeight: Int
        get() {
            val activity = activity ?: return 0
            return activity.findViewById<View>(android.R.id.content).height
        }

    private fun setDummyData(listView: ListView) {
        for (i in 1..10) {
            dummyData.add("Item$i")
        }
        listView.adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, dummyData)
    }

    protected fun setDummyDataWithHeader(listView: ListView, headerView: View) {
        listView.addHeaderView(headerView)
        setDummyData(listView)
    }

    protected fun setDummyData(recyclerView: RecyclerView) {
        for (i in 1..10) {
            dummyData.add("Item$i")
        }
        recyclerView.adapter = SimpleRecyclerAdapter(requireActivity(), dummyData)
    }

//    protected fun setDummyDataWithHeader(recyclerView: RecyclerView, headerView: View) {
//        for (i in 1..10) {
//            dummyData.add("Item$i")
//        }
//        recyclerView.adapter = SimpleHeaderRecyclerAdapter(getActivity(), dummyData, headerView)
//    }
}