package com.ssafy.gumipresso_admin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListItemBannerBinding
import com.ssafy.gumipresso_admin.model.dto.Banner
import com.ssafy.gumipresso_admin.viewmodel.BannerViewModel

class BannerAdapter(var list: MutableList<Banner>): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemBannerBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.bannerVM = BannerViewModel()
        }
        fun bind(item: Banner, view: View, position: Int){
            binding.bannerVM!!.setBannerItem(item)
            Log.d("TAG", "bind: $item")
            binding.cardviewMember.setOnClickListener {
                onClickBannerListener.onClick(view, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBannerBinding=
            ListItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemBannerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onClickBannerListener: OnClickBannerListener
    interface OnClickBannerListener{
        fun onClick(view: View, position: Int)
    }
}