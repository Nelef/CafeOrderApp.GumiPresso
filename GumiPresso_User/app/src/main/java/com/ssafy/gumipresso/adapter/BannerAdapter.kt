package com.ssafy.gumipresso.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListBannerItemBinding
import com.ssafy.gumipresso.model.dto.Banner

private const val TAG = "BannerAdapter"
class BannerAdapter(var list: List<Banner>) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ListBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Banner) {
            binding.banner = item
        }
        fun bindClickListener(listener: BannerAdapter.OnBannerItemClick) {
            binding.root.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listBannerItemBinding =
            ListBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listBannerItemBinding).apply {
            bindClickListener(onBannerItemClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $list")
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onBannerItemClick: OnBannerItemClick
    interface OnBannerItemClick{
        fun onClick(view: View, position: Int)
    }
}