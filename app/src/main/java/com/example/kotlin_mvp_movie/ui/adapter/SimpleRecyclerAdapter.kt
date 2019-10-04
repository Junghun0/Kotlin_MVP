package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_mvp_movie.R
import java.util.*

class SimpleRecyclerAdapter(context: Context, items: ArrayList<String> ) : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>() {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mItems: ArrayList<String> = items

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_cardview_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = "dd"
        holder.textView2.text = "aa"
//        holder.textView.text = mItems[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var textView: TextView = view.findViewById<View>(R.id.text1) as TextView
        var textView: TextView = view.findViewById(R.id.detail_keyword)
        var textView2: TextView = view.findViewById(R.id.detail_openDate)

    }
}