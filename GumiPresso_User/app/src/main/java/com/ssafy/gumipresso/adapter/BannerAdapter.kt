package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.R

class BannerAdapter(var list: MutableList<Int>) : RecyclerView.Adapter<BannerAdapter.DefaultViewHolder>() {

    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView_idol = itemView.findViewById<ImageView>(R.id.banner1)

        fun onBind(res: Int) {
            imageView_idol.setImageResource(res)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_banner_item, parent, false)
        return DefaultViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}