package com.ssafy.gumipresso_admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListItemManageBinding

class ManageAdapter(val list: List<Map<String, Any>>): RecyclerView.Adapter<ManageAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemManageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Map<String, Any>, view: View, position: Int){
            binding.cardviewMember.setOnClickListener {
                onManageItemClickListener.onClick(view, position)
            }
            binding.tvManageTitle.setText(item["title"].toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemManageBinding =
            ListItemManageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemManageBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onManageItemClickListener: OnManageItemClickListener
    interface OnManageItemClickListener{
        fun onClick(view: View, position: Int)
    }
}