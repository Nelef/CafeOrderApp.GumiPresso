package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListTableItemBinding
import com.ssafy.gumipresso.model.dto.Table
import com.ssafy.gumipresso.viewmodel.TableViewModel


private const val TAG = "TableAdapter"
class TableAdapter(val list: List<Table>): RecyclerView.Adapter<TableAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ListTableItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.table = TableViewModel()
        }
        fun bind(item: Table) {
            binding.table!!.setTableItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listTableItemBinding =
            ListTableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listTableItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

}