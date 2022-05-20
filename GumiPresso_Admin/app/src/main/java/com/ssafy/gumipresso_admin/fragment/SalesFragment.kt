package com.ssafy.gumipresso_admin.fragment

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.adapter.SalesAdapter
import com.ssafy.gumipresso_admin.databinding.FragmentSalesBinding
import com.ssafy.gumipresso_admin.model.dto.DateDTO
import com.ssafy.gumipresso_admin.model.dto.Sales
import com.ssafy.gumipresso_admin.viewmodel.SalesViewModel
import com.ssafy.gumipresso_amdin.util.DateFormatUtil

private const val TAG ="SalesFragment"
class SalesFragment : Fragment() {
    private lateinit var binding: FragmentSalesBinding
    private val salesViewModel: SalesViewModel by viewModels()

    private lateinit var salesList: MutableList<Sales>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        binding.constContent.setOnClickListener {
            salesViewModel.setFlagDatePickOpenValue()
        }
    }

    private fun initViewModel(){
        salesViewModel.getSalesList("year", DateDTO("2022","5","20"))
        salesViewModel.salesList.observe(viewLifecycleOwner){
            salesList = it
            if(it.size > 0){
                salesViewModel.setSalesItem(salesList[0])
            }
            initAdapter()
        }
        salesViewModel.flagDatePickOpen.observe(viewLifecycleOwner){
            binding.salesVM = salesViewModel
        }
    }

    private fun initAdapter(){
        val salesAdapter = SalesAdapter(salesList)
        binding.recyclerSales.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = salesAdapter
        }
    }

}