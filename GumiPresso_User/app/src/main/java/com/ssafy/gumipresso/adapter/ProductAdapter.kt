package com.ssafy.gumipresso.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListProductItemBinding
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.viewmodel.ProductViewModel

private const val TAG = "ProductAdapter"
class ProductAdapter(val list: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.product = item
        }
        fun bindClickListener(listener: OnProductItemClick) {
            binding.root.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listProductItemBinding =
            ListProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listProductItemBinding).apply {
            bindClickListener(onProductItemClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onProductItemClick: OnProductItemClick
    interface OnProductItemClick{
        fun onClick(view: View, position: Int)
    }
}