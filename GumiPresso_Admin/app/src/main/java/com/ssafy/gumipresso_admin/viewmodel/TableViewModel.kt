package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "TableViewModel"
class TableViewModel : ViewModel() {
    private val _tableList = MutableLiveData<List<Table>>()
    val tableList: LiveData<List<Table>>
        get() = _tableList

    private val _table = MutableLiveData<Table>()
    val table : LiveData<Table>
        get() = _table
    fun setTableItem(table: Table){
        _table.value = table
    }

    private val _flagTableChange = MutableLiveData<Boolean>(false)
    val flagTableChange : LiveData<Boolean>
        get() = _flagTableChange
    fun setflagTableState(){
        Log.d(TAG, "setflagTableState: ")
        _flagTableChange.value = !_flagTableChange.value!!
    }
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
                    setflagTableState()
                }
            }catch (e: Exception){
                Log.d(TAG, "getOrdertable: ${e.message}")
            }
        }
    }

    private val _remainTable = MutableLiveData<String>("이용중인 테이블: 6/10")
    val remainTable: LiveData<String>
        get() = _remainTable
    fun setRemainTableItem(){
        var useTable = 0
        for(i in _tableList.value!!.indices){
            if(_tableList.value!!.get(i).state){
                useTable++
            }
        }
        _remainTable.value = "이용중인 테이블: ${useTable}/${tableList.value!!.size}"
    }
}



















