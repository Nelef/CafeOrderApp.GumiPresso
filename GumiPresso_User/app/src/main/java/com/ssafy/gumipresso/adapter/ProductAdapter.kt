package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListProductItemBinding
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.viewmodel.ProductViewModel

class ProductAdapter(val list: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.productViewModel = ProductViewModel()
        }
        fun bind(item: Product){
            binding.productViewModel!!.setProductItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listProductItemBinding =
            ListProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listProductItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onProductItemClick.onClick(holder.itemView, position) }
    }

    override fun getItemCount(): Int = list.size

    lateinit var onProductItemClick: OnProductItemClick
    interface OnProductItemClick{
        fun onClick(view: View, position: Int)
    }

}