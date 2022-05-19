package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListOrderDetailItemBinding
import com.ssafy.gumipresso.model.dto.RecentOrderDetail
import com.ssafy.gumipresso.viewmodel.RecentOrderDetailViewModel

class RecentOrderDetailAdapter(val list: List<RecentOrderDetail>): RecyclerView.Adapter<RecentOrderDetailAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListOrderDetailItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.orderDetailViewModel = RecentOrderDetailViewModel()
        }
        fun bind(item: RecentOrderDetail){
            binding.orderDetailViewModel!!.setRecentOrderDetail(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listOrderDetailItemBinding =
            ListOrderDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listOrderDetailItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}