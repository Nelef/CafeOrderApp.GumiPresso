package com.ssafy.gumipresso_admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListItemProductBinding
import com.ssafy.gumipresso_admin.model.dto.Product
import com.ssafy.gumipresso_admin.viewmodel.ProductViewModel

class ProductAdapter(val list: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemProductBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.productVM = ProductViewModel()
        }
        fun bind(item: Product, view: View, position: Int){
            binding.apply {
                productVM!!.setProductItem(item)
                ivOrderImg.setOnClickListener {
                    onClickProductItemListener.onClick(view, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val listItemProductBinding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemProductBinding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onClickProductItemListener: OnClickProductItemListener
    interface OnClickProductItemListener{
        fun onClick(view: View, position: Int)
    }
}