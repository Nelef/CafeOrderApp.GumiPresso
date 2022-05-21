package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListNoticeItemBinding

class NoticeAdapter(val list: List<String>) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ListNoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvNotice.text = item
        }

        fun bindClickListener(listener: OnDeleteButtonClick) {
            binding.btnMenuDelete.setOnClickListener {
                listener.onDeleteClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listNoticeItemBinding =
            ListNoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listNoticeItemBinding).apply {
            bindClickListener(onDeleteButtonClickListener)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onDeleteButtonClickListener: OnDeleteButtonClick

    interface OnDeleteButtonClick {
        fun onDeleteClick(view: View, position: Int)
    }
}