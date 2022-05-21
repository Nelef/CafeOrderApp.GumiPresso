package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "TableViewModel"
class TableViewModel : ViewModel() {
    private val _tableList = MutableLiveData<List<Table>>()
    val tableList: LiveData<List<Table>>
        get() = _tableList

    fun setOrdertable(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Retrofit.tableService.setOrdertable(id)
                if(response.isSuccessful && response.body() != null){
                    _tableList.postValue(response.body() as List<Table>)
                }
            }catch (e: Exception){
                Log.d(TAG, "setOrdertable: ${e.message}")
            }
        }
    }

    fun getOrdertable() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Retrofit.tableService.getOrdertableList()
                if(response.isSuccessful && response.body() != null){
                    _tableList.postValue(response.body() as List<Table>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getOrdertable: ${e.message}")
            }
        }
    }
}