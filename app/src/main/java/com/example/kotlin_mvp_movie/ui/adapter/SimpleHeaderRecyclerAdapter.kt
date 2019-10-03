package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SimpleHeaderRecyclerAdapter(
    context: Context,
    private val mItems: ArrayList<String>,
    private val mHeaderView: View?
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

    private val mInflater =  LayoutInflater.from(context)


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            HeaderViewHolder(mHeaderView!!)
        } else {
            ItemViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            (viewHolder).textView.text = mItems[position - 1]
        }
    }

    internal class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById<View>(android.R.id.text1) as TextView
    }
}