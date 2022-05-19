package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.RecentOrder
import com.ssafy.gumipresso_amdin.util.DateFormatUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="OrderViewModel"
class OrderViewModel: ViewModel() {
    private val _orderList = MutableLiveData<MutableList<RecentOrder>>()
    val orderList : LiveData<MutableList<RecentOrder>>
        get() = _orderList

    fun getOrderListByCompleted(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.orderService.getOrderListByCompleted()
                if(response.isSuccessful && response.body() != null){
                    Log.d(TAG, "getOrderListByCompleted: ${response.body()}")
                    _orderList.postValue(response.body() as MutableList<RecentOrder>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getOrderListByCompleted: ${e.message}")
            }
        }
    }

    private val _recentOrder = MutableLiveData<RecentOrder>()
    val recentOrder : LiveData<RecentOrder>
        get() = _recentOrder
    fun setOrders(order: RecentOrder){
        _recentOrder.value = order
    }

    private val _totalQuantity = MutableLiveData<String>()
    val totalQuantity : LiveData<String>
        get() = _totalQuantity
    private val _totalPrice = MutableLiveData<String>()
    val totalPrice : LiveData<String>
        get() = _totalPrice
    private val _totalTitle = MutableLiveData<String>()
    val totalTitle: LiveData<String>
        get() = _totalTitle
    private val _orderId = MutableLiveData<String>()
    val orderId : LiveData<String>
        get() = _orderId
    private val _orderTime = MutableLiveData<String>()
    val orderTime : LiveData<String>
        get() = _orderTime
    fun getTotalValue(){
        val detailList = (_recentOrder.value as RecentOrder).recentOrderDetail
        var quantity=0
        var price = 0
        for(i in detailList.indices){
            quantity += detailList[i].quantity
            price += detailList[i].price
        }
        _totalPrice.value = "${price} 원"
        _totalQuantity.value = "총 ${quantity} 잔"
        if(detailList.size > 1) {
            _totalTitle.value = "${detailList[0].name} 외 ${quantity - 1}잔"
        }else{
            _totalTitle.value = detailList[0].name
        }
        _orderId.value = "주문번호: ${(_recentOrder.value as RecentOrder).oId}"
        _orderTime.value = DateFormatUtil.convertYYMMDDHHMM((_recentOrder.value as RecentOrder).orderTime)
    }
}












