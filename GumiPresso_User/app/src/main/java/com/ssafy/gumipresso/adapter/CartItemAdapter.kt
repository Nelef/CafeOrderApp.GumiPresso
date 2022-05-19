package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListCartItemBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.viewmodel.CartViewModel

class CartItemAdapter(val cartList: List<Cart>): RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListCartItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.cart = CartViewModel()
        }
        fun bind(item: Cart, view: View, position: Int){
            binding.apply {
                cart!!.setCartItem(item)
                btnMenuDelete.setOnClickListener {
                    onDeleteButtonClick.onClick(view, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listCartItemBinding =
            ListCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listCartItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cartList[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = cartList.size

    lateinit var onDeleteButtonClick: OnDeleteButtonClick
    interface OnDeleteButtonClick{
        fun onClick(view: View, position: Int)
    }
}