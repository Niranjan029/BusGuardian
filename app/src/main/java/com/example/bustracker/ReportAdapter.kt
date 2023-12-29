package com.example.bustracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(private val myList:ArrayList<PostItem>,private val context:Context):RecyclerView.Adapter<ReportAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(context).inflate(R.layout.report,parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currPost = myList[position]
        holder.postText.text = currPost.text
    }
    override fun getItemCount(): Int
    {
            return myList.size
    }
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        val postText:TextView = itemview.findViewById(R.id.postTv)
    }
}