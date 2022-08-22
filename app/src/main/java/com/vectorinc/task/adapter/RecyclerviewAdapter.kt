package com.vectorinc.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vectorinc.task.R
import com.vectorinc.task.domain.model.Character

class RecyclerviewAdapter(dataList : List<Character>, context: Context) : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {
    private val list = dataList
    private val context = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemText: TextView = view.findViewById(R.id.name)
        var image : ImageView = view.findViewById(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.itemText.text = data.name
        glideImage(data.img, context = context,holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun glideImage(imageUrl : String, context: Context, imageView: ImageView){
        Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }


}