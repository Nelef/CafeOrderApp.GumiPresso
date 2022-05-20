package com.ssafy.gumipresso_admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListItemOrderDetailBinding
import com.ssafy.gumipresso_admin.model.dto.OrderDetail
import com.ssafy.gumipresso_admin.viewmodel.OrderViewModel

class OrderDetailAdapter(val list: List<OrderDetail>) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemOrderDetailBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.detailVM = OrderViewModel()
        }
        fun bind(item: OrderDetail, view: View, position: Int){
            binding.detailVM!!.setOrderDetailItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemOrderDetailBinding =
            ListItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemOrderDetailBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onDoneButtonClickListener: OnDoneButtonClickListener
    interface OnDoneButtonClickListener{
        fun onDoneButtonClick(view: View, position: Int)
    }
}