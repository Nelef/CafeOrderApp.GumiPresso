package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.RecentOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RecentOrderViewModel"

class RecentOrderViewModel : ViewModel() {
    private val _recentOrderList = MutableLiveData<MutableList<RecentOrder>>()
    val recentOrderList: LiveData<MutableList<RecentOrder>>
        get() = _recentOrderList

    private val _recentOrder = MutableLiveData<RecentOrder>()
    val recentOrder: LiveData<RecentOrder>
        get() = _recentOrder

    fun getOrderList(user_id: String) = viewModelScope.launch(Dispatchers.Main) {
        val response = Retrofit.orderService.getOrderList(user_id)
        if (response.isSuccessful && response.body() != null) {
            _recentOrderList.postValue(response.body() as MutableList<RecentOrder>)
        }
        Log.i(TAG, "recentOrderList: ${recentOrderList.value}")
    }

    fun setRecentOrder(recentOrder: RecentOrder) {
        _recentOrder.value = recentOrder
    }


}