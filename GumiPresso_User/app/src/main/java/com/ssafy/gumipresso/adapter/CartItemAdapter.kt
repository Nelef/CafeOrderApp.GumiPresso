package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListCartItemBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.viewmodel.CartViewModel

class CartItemAdapter(val cartList: List<Cart>): RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListCartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cart){
            binding.cart = item
        }
        fun bindClickListener(listener: OnDeleteButtonClick) {
            binding.btnMenuDelete.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listCartItemBinding =
            ListCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listCartItemBinding).apply {
            bindClickListener(onDeleteButtonClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cartList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cartList.size

    lateinit var onDeleteButtonClick: OnDeleteButtonClick
    interface OnDeleteButtonClick{
        fun onClick(view: View, position: Int)
    }
}