package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_mvp_movie.R

class GalleryViewPagerAdapter(private var mGallery: ArrayList<String>, val context: Context) : RecyclerView.Adapter<GalleryViewPagerAdapter.GalleryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_viewpager_layout, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGallery.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Glide.with(context).load(mGallery[position]).into(holder.galleryImage)
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val galleryImage: ImageView = itemView.findViewById(R.id.gallery_item)
    }

}