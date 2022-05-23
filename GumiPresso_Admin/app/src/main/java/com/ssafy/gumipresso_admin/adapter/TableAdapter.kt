package com.ssafy.gumipresso_admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso_admin.databinding.ListTableItemBinding
import com.ssafy.gumipresso_admin.model.dto.Table
import com.ssafy.gumipresso_admin.viewmodel.TableViewModel


private const val TAG = "TableAdapter"

class TableAdapter(val list: List<Table>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListTableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.table = TableViewModel()
        }

        fun bind(item: Table, view: View, position: Int) {
            binding.table!!.setTableItem(item)
            binding.apply {
                cardviewMember.setOnClickListener {
                    onTableClickListner.onClick(view, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listTableItemBinding =
            ListTableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listTableItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, holder.itemView, position)
    }

    override fun getItemCount(): Int = list.size

    lateinit var onTableClickListner: OnTableClickListner

    interface OnTableClickListner {
        fun onClick(view: View, position: Int)
    }
}