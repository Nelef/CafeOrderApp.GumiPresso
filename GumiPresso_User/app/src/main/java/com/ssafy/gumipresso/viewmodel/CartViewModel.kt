package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.Order
import com.ssafy.gumipresso.model.dto.AosOrderDetail
import com.ssafy.gumipresso.model.dto.OrderForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="CartViewModel"
class CartViewModel: ViewModel() {

    private val _cartList = MutableLiveData<MutableList<Cart>>(mutableListOf())
    val cartList : LiveData<MutableList<Cart>>
        get() = _cartList

    fun addCart(cart: Cart){
        val position = isExist(cart.menuId)
        if( position != -1){
            (_cartList.value as MutableList<Cart>)[position].addCartItem(cart)
        }
        else{
            (_cartList.value as MutableList<Cart>).add(cart)
        }
        setCartTotalValue()
    }

    fun removeCartItem(position: Int){
        (_cartList.value as MutableList<Cart>).removeAt(position)
        setCartTotalValue()
    }

    fun clearCart(){
        (_cartList.value as MutableList<Cart>).clear()
        setCartTotalValue()
    }


    fun orderCart(userId: String, orderTable: String){
        val list = _cartList.value as MutableList<Cart>
        val orderDetailList = mutableListOf<AosOrderDetail>()
        val order = Order(userId, orderTable)
        for(i in list.indices){
            orderDetailList.add(AosOrderDetail(list[i].menuId, list[i].menuCnt))
        }
        val orderForm = OrderForm(order, orderDetailList)
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.orderService.order(orderForm)
                clearCart()
            }catch (e: Exception){
                Log.d(TAG, "orderCart: ${e.message}")
            }
        }
    }

    private fun isExist(menuId: Int): Int{
        val list = cartList.value as List<Cart>
        for(i in list.indices){
            if(menuId == list[i].menuId){
                return i
            }
        }
        return -1
    }
    
    private val _totalCartPrice = MutableLiveData<Int>()
    val totalCartPrice : LiveData<Int>
        get() = _totalCartPrice

    private val _totalCartQuantity = MutableLiveData<Int>()
    val totalCartQuantity: LiveData<Int>
        get() = _totalCartQuantity

    init {
        _totalCartPrice.value = 0
        _totalCartQuantity.value = 0
    }

    fun setCartTotalValue(){
        var quantity = 0;
        var price = 0;
        for(cart in (_cartList.value as List<Cart>)){
            quantity += cart.menuCnt
            price += cart.totalPrice
        }
        _totalCartPrice.value = price
        _totalCartQuantity.value= quantity
    }

    private val _cart = MutableLiveData<Cart>()
    val cart : LiveData<Cart>
        get() = _cart
    fun setCartItem(cart: Cart){
        _cart.value = cart
    }

    private val _isTakeOut = MutableLiveData<Boolean>(true)
    val isTakeOut : LiveData<Boolean>
        get() = _isTakeOut
    fun setHereOrTogo(takeOut: Boolean){
        _isTakeOut.postValue(takeOut)
    }
}











