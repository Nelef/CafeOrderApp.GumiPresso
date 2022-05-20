package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.DateDTO
import com.ssafy.gumipresso_admin.model.dto.Sales
import com.ssafy.gumipresso_amdin.util.DateFormatUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="SalesViewModel"
class SalesViewModel: ViewModel() {
    private val _salesList = MutableLiveData<MutableList<Sales>>()
    val salesList : LiveData<MutableList<Sales>>
        get() = _salesList
    
    fun getSalesList(format: String, dateDTO: DateDTO){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.salesService.getSales(format, dateDTO)
                if (response.isSuccessful && response.body() != null){
                    _salesList.postValue(response.body() as MutableList<Sales>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getSalesList: ${e.message}")
            }
        }
    }

    private val _sales = MutableLiveData<Sales>()
    val sales : LiveData<Sales>
        get() = _sales
    fun setSalesItem(sales: Sales){
        _sales.value = sales
        setSalesValue()
    }

    private val _salesDate = MutableLiveData<String>()
    val salesDate : LiveData<String>
        get() = _salesDate

    fun setSalesValue(){
        val sales = _sales.value as Sales
        _salesDate.value = DateFormatUtil.convertSalesData(sales)
    }

    private val _flagDatePickOpen = MutableLiveData<Boolean>(true)
    val flagDatePickOpen : LiveData<Boolean>
        get() = _flagDatePickOpen
    fun setFlagDatePickOpenValue(){
        _flagDatePickOpen.value = !_flagDatePickOpen.value!!
    }

    private val _flagTabLayoutSelect = MutableLiveData<String>("month")
    val flagTabLayoutSelect: LiveData<String>
        get() = _flagTabLayoutSelect
    fun setflagTabLayoutSelected(flag: String){
        _flagTabLayoutSelect.value = flag
    }

    private val _dateDTO = MutableLiveData<DateDTO>()
    val dateDTO : LiveData<DateDTO>
        get() = _dateDTO
    fun setDateDtoItem(dateDTO: DateDTO){
        _dateDTO.value = dateDTO
    }

    private val _selectTitleText = MutableLiveData<String>("년도 선택")
    val selectTitleText : LiveData<String>
        get() = _selectTitleText
    fun setTitleText(selectedItem: String){
        var text = ""
        when(selectedItem){
            "year" -> text = "년도 선택"
            "month" -> text = "월 선택"
            "day" -> text = "일 선택"
        }
        _selectTitleText.value = text
    }
}