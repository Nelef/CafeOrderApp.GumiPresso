package com.ssafy.gumipresso_admin.fragment

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.adapter.SalesAdapter
import com.ssafy.gumipresso_admin.databinding.FragmentSalesBinding
import com.ssafy.gumipresso_admin.model.dto.DateDTO
import com.ssafy.gumipresso_admin.model.dto.Sales
import com.ssafy.gumipresso_admin.viewmodel.SalesViewModel
import com.ssafy.gumipresso_amdin.util.DateFormatUtil
import java.util.*

private const val TAG ="SalesFragment"
class SalesFragment : Fragment() {
    private lateinit var binding: FragmentSalesBinding
    private val salesViewModel: SalesViewModel by viewModels()

    private lateinit var timeFlag: String
    private lateinit var typeFlag: String
    private lateinit var salesList: MutableList<Sales>
    private lateinit var dateDTO: DateDTO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timeFlag = "year"
        typeFlag = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        initViewModel()

        binding.btnType.setOnClickListener {
            salesViewModel.setTypeSelect()
        }
        binding.constContent.setOnClickListener {
            salesViewModel.setFlagDatePickOpenValue()
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var flag = ""
                when(tab!!.position){
                    0 -> flag = "year"
                    1 -> flag = "month"
                    2 -> flag = "day"
                }
                timeFlag = flag
                salesViewModel.setflagTabLayoutSelected(flag)
                salesViewModel.setTitleText(flag)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

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
        salesViewModel.flagTabLayoutSelect.observe(viewLifecycleOwner){
            timeFlag = it
            salesViewModel.getSalesList(timeFlag+typeFlag, dateDTO)
            binding.salesVM = salesViewModel
        }
        salesViewModel.isTypeSelected.observe(viewLifecycleOwner){
            typeFlag = if(it) "type" else ""
            salesViewModel.getSalesList(timeFlag+typeFlag, dateDTO)
            binding.salesVM = salesViewModel
        }
        salesViewModel.dateDTO.observe(viewLifecycleOwner){
            salesViewModel.getSalesList(timeFlag+typeFlag, dateDTO)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDatePicker(){
        binding.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            dateDTO = DateDTO(year.toString(), (monthOfYear+1).toString(), dayOfMonth.toString())
            salesViewModel.setFlagDatePickOpenValue()
            salesViewModel.setDateDtoItem(dateDTO)
        }
        val calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
            time.time = System.currentTimeMillis()
        }
        binding.datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }

}