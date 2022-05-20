package com.ssafy.gumipresso_admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListItemSalesBinding
import com.ssafy.gumipresso_admin.model.dto.Sales
import com.ssafy.gumipresso_admin.viewmodel.SalesViewModel

class SalesAdapter(val list: MutableList<Sales>): RecyclerView.Adapter<SalesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemSalesBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.salesVM = SalesViewModel()
        }
        fun bind(item: Sales){
            binding.salesVM!!.setSalesItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemSalesBinding =
            ListItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemSalesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}