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
        init {
            binding.orderViewModel = RecentOrderViewModel()
        }

        fun bind(listItem: RecentOrder, holder: ViewHolder, position: Int) {
            binding.apply {
                orderViewModel!!.setRecentOrder(listItem)
                listOrderlist.setOnClickListener {
                    onOrderItemClick.onClick(holder.itemView, position);
                }
                ivCart.setOnClickListener {
                    onCartIconClick.onClick(holder.itemView, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRecentOrderItemBinding =
            ListRecentOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listRecentOrderItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]
        holder.bind(listItem, holder, position)
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