package com.ssafy.gumipresso_admin.fragment

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.tabs.TabLayout
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.adapter.SalesAdapter
import com.ssafy.gumipresso_admin.databinding.FragmentSalesBinding
import com.ssafy.gumipresso_admin.model.dto.DateDTO
import com.ssafy.gumipresso_admin.model.dto.Sales
import com.ssafy.gumipresso_admin.viewmodel.SalesViewModel
import com.ssafy.gumipresso_amdin.util.DateFormatUtil
import java.time.Year
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
        initViewModel()
        initDatePicker()
        binding.btnType.setOnClickListener {
            salesViewModel.setTypeSelect()
        }
        binding.constContent.setOnClickListener {
            salesViewModel.setFlagDatePickOpenValue()
        }
        binding.tabLayout.getTabAt(1)!!.select()
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
        //salesViewModel.getSalesList("year", DateDTO(cal.get(Calendar.YEAR).toString(),cal.get(Calendar.MONTH + 1).toString(),cal.get(Calendar.DATE).toString()))
        salesViewModel.salesList.observe(viewLifecycleOwner){
            salesList = it
            if(it.size > 0){
                salesViewModel.setSalesItem(salesList[0])
            }
            Log.d(TAG, "initViewModel: ${salesList}")
            if(salesList.size > 0) {
                setChartView()
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


    private fun setChartView() {
        var chartWeek = binding.barChart
        setWeek(chartWeek)
    }

    private fun initBarDataSet(barDataSet: BarDataSet) {
        //Changing the color of the bar
        barDataSet.color = Color.parseColor("#304567")
        //Setting the size of the form in the legend
        barDataSet.formSize = 15f
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false)
        //setting the text size of the value of the bar
        barDataSet.valueTextSize = 12f
    }

    private fun setWeek(barChart: BarChart) {
        initBarChart(barChart)

        barChart.setScaleEnabled(true)

        val entries: ArrayList<BarEntry> = ArrayList()
        val title = "매출"

        for (i in 0 until salesList.size) {
            var barEntry: BarEntry? = null
            when(timeFlag){
                "year" -> barEntry = BarEntry(salesList[i].month!!.toFloat(), salesList[i].total.toFloat() )
                "month" -> barEntry = BarEntry(salesList[i].day!!.toFloat(), salesList[i].total.toFloat() )
                "day" -> barEntry = BarEntry(salesList[i].hour!!.toFloat(), salesList[i].total.toFloat() )
            }
            entries.add(barEntry!!)
        }
        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
    }

    private fun initBarChart(barChart: BarChart) {
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        barChart.setDescription(description)

        //X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)


        //바텀 좌표 값
        val xAxis: XAxis = barChart.getXAxis()
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        xAxis.textColor = requireActivity().resources.getColor(R.color.coffee_dark_brown)
        xAxis.textSize = 16F
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)


        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.BLACK


        //우측 값 hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = barChart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.textColor = Color.BLACK


        //바차트의 타이틀
        val legend: Legend = barChart.getLegend()
        //setting the shape of the legend form to line, default square shape
        legend.form = Legend.LegendForm.LINE
        //setting the text size of the legend
        legend.textSize = 20f
        legend.textColor = Color.BLACK
        //setting the alignment of legend toward the chart
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        //setting the stacking direction of legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false)
    }

}