package com.example.kotlin_mvp_movie

import android.util.TypedValue
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_mvp_movie.ui.adapter.SimpleHeaderRecyclerAdapter
import com.example.kotlin_mvp_movie.ui.adapter.SimpleRecyclerAdapter
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    protected val actionBarSize: Int
        get() {
            val typedValue = TypedValue()
            val textSizeAttr = intArrayOf(R.attr.actionBarSize)
            val indexOfAttrTextSize = 0
            val a = obtainStyledAttributes(typedValue.data, textSizeAttr)
            val actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1)
            a.recycle()
            return actionBarSize
        }

    protected val screenHeight: Int
        get() = findViewById<View>(android.R.id.content).height

    protected fun setDummyDataFew(listView: ListView) {
        setDummyData(listView, NUM_OF_ITEMS_FEW)
    }

    @JvmOverloads
    protected fun setDummyData(listView: ListView, num: Int = NUM_OF_ITEMS) {
        listView.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, getDummyData(num))
    }

    @JvmOverloads
    protected fun setDummyDataWithHeader(
        listView: ListView,
        headerHeight: Int,
        num: Int = NUM_OF_ITEMS
    ) {
        val headerView = View(this)
        headerView.layoutParams =
            AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight)
        headerView.minimumHeight = headerHeight
        // This is required to disable header's list selector effect
        headerView.isClickable = true
        setDummyDataWithHeader(listView, headerView, num)
    }

    private fun setDummyDataWithHeader(listView: ListView, headerView: View, num: Int) {
        listView.addHeaderView(headerView)
        setDummyData(listView, num)
    }

    protected fun setDummyData(gridView: GridView) {
        gridView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dummyData)
    }

    protected fun setDummyDataFew(recyclerView: RecyclerView) {
        setDummyData(recyclerView, NUM_OF_ITEMS_FEW)
    }

    @JvmOverloads
    protected fun setDummyData(recyclerView: RecyclerView, num: Int = NUM_OF_ITEMS) {
        recyclerView.adapter = SimpleRecyclerAdapter(this, getDummyData(num))
    }

    protected fun setDummyDataWithHeader(recyclerView: RecyclerView, headerHeight: Int) {
        val headerView = View(this)
        headerView.layoutParams =
            AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight)
        headerView.minimumHeight = headerHeight
        // This is required to disable header's list selector effect
        headerView.isClickable = true
        setDummyDataWithHeader(recyclerView, headerView)
    }

    private fun setDummyDataWithHeader(recyclerView: RecyclerView, headerView: View) {
        recyclerView.adapter = SimpleHeaderRecyclerAdapter(this, dummyData, headerView)
    }

    companion object {
        private const val NUM_OF_ITEMS = 100
        private const val NUM_OF_ITEMS_FEW = 3

        val dummyData: ArrayList<String>
            get() = getDummyData(NUM_OF_ITEMS)

        fun getDummyData(num: Int): ArrayList<String> {
            val items = ArrayList<String>()
            for (i in 1..num) {
                items.add("Item $i")
            }
            return items
        }
    }
}