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
    private val _table = MutableLiveData<Table>()
    val table: LiveData<Table>
        get() = _table

    fun setOrdertable(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Retrofit.tableService.setOrdertable(id)
                if(response.isSuccessful && response.body() != null){
                    _table.postValue(response.body() as Table)
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
                    _table.postValue(response.body() as Table)
                }
            }catch (e: Exception){
                Log.d(TAG, "setOrdertable: ${e.message}")
            }
        }
    }
}