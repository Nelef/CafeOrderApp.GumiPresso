package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.RecentOrderDetail
import com.ssafy.gumipresso.util.DateFormatUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "RecentOrderDetailViewMd"
class RecentOrderDetailViewModel: ViewModel() {
    private val _recentOrder = MutableLiveData<RecentOrder>()
    val recentOrder: LiveData<RecentOrder>
        get() = _recentOrder

    fun getRecentOrderDetail(order_id: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.orderService.getOrderDetailList(order_id)
                if(response.isSuccessful && response.body() != null){
                    _recentOrder.postValue(response.body() as RecentOrder)
                }
                else{

                }
            }catch (e: Exception){
                Log.d(TAG, "getRecentOrderDetail: ${e.message}")
            }
        }
    }

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice : LiveData<String>
        get() = _totalPrice

    fun getTotalPriceOrderDetail(){
        var totalPrice = 0
        for(recentOrderDetail in (_recentOrder.value as RecentOrder).recentOrderDetail){
            totalPrice += recentOrderDetail.price * recentOrderDetail.quantity
        }
        _totalPrice.value = "$totalPrice 원"
    }

    private val _recentOrderDetail = MutableLiveData<RecentOrderDetail>()
    val recentOrderDetail: LiveData<RecentOrderDetail>
        get() = _recentOrderDetail
    fun setRecentOrderDetail(recentOrderDetail: RecentOrderDetail){
        _recentOrderDetail.value = recentOrderDetail
    }

    private val _orderTime = MutableLiveData<String>()
    val orderTime : LiveData<String>
        get() = _orderTime
    fun setOrderTimeToString(){
        _orderTime.value = (DateFormatUtil.convertYYMMDDHHMM(_recentOrder.value!!.orderTime))
    }

}