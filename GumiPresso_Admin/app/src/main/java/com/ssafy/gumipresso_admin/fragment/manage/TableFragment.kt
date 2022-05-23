package com.ssafy.gumipresso_admin.fragment.manage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.adapter.TableAdapter
import com.ssafy.gumipresso_admin.databinding.FragmentTableBinding
import com.ssafy.gumipresso_admin.model.dto.Table
import com.ssafy.gumipresso_admin.viewmodel.TableViewModel

private const val TAG ="TableFragment"
class TableFragment : Fragment() {
    private lateinit var binding: FragmentTableBinding
    private val tableViewModel: TableViewModel by viewModels()

    private lateinit var tableAdapter: TableAdapter
    private lateinit var tableList : List<Table>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        tableViewModel.getOrdertable()
        tableViewModel.tableList.observe(viewLifecycleOwner){
            tableList = it
            tableViewModel.setRemainTableItem()
            initTableAdapter()
        }
        tableViewModel.remainTable.observe(viewLifecycleOwner){
            binding.tableVM = tableViewModel
        }
    }
    private fun initTableAdapter(){
        tableAdapter = TableAdapter(tableList)
        tableAdapter.onTableClickListner = object : TableAdapter.OnTableClickListner{
            override fun onClick(view: View, position: Int) {
                tableViewModel.setOrdertable(position + 1)
            }
        }
        binding.recyclerTableList.apply {
            layoutManager = GridLayoutManager (context, 5)
            adapter = tableAdapter
        }
    }

}