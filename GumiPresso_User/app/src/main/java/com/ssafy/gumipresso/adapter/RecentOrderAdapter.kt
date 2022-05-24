package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListRecentOrderItemBinding
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel

private const val TAG = "RecentOrderAdapter"

class RecentOrderAdapter(private val list: List<RecentOrder>) :
    RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListRecentOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentOrder) {
            binding.recentOrder = item
        }
        fun bindClickListener(listener: OnOrderItemClick) {
            binding.listOrderlist.setOnClickListener {
                listener.onClick(it, list.size - adapterPosition - 1);
            }
        }
        fun bindClickListener(listener: OnCartIconClick) {
            binding.ivCart.setOnClickListener {
                listener.onClick(it, list.size - adapterPosition - 1);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRecentOrderItemBinding =
            ListRecentOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listRecentOrderItemBinding).apply {
            bindClickListener(onOrderItemClick)
            bindClickListener(onCartIconClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[list.size - position - 1]
        holder.bind(item)
    }

    lateinit var onOrderItemClick: OnOrderItemClick

    interface OnOrderItemClick {
        fun onClick(view: View, position: Int)
    }

    lateinit var onCartIconClick: OnCartIconClick

    interface OnCartIconClick {
        fun onClick(view: View, position: Int)
    }


    override fun getItemCount(): Int = list.size
}