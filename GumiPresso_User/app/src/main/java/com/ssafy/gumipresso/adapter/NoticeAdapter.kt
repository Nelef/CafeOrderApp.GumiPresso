package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListNoticeItemBinding

class NoticeAdapter(val list: List<String>): RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListNoticeItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String, holder: ViewHolder, position: Int){
            binding.apply {
                tvNotice.text = item
                btnMenuDelete.setOnClickListener {
                    onDeleteButtonClickListener.onDeleteClick(holder.itemView, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listNoticeItemBinding =
            ListNoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listNoticeItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onDeleteButtonClickListener: OnDeleteButtonClick

    interface OnDeleteButtonClick{
        fun onDeleteClick(view: View, position: Int)
    }
}