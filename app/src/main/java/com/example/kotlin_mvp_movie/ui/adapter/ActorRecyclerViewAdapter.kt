package com.example.kotlin_mvp_movie.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.details.Staff

class ActorRecyclerViewAdapter (private var mActors: ArrayList<Staff>, val context: Context) : RecyclerView.Adapter<ActorRecyclerViewAdapter.ActorViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actor_recycler_item, parent, false)
        return ActorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mActors.size
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        Log.e("actor List adapter",""+mActors)
        holder.actorNameTextView.text = mActors[position].staffNm
        holder.actorNameRollTextView.text = mActors[position].staffRole
        holder.actorNameRollGroup.text = mActors[position].staffRoleGroup
    }

    class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val actorNameRollTextView: TextView = itemView.findViewById(R.id.actor_name_roll)
        val actorNameTextView: TextView = itemView.findViewById(R.id.actor_name_item)
        val actorNameRollGroup: TextView = itemView.findViewById(R.id.actor_roll_group)
    }

}