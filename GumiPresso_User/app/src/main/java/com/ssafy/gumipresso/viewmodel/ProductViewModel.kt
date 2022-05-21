package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="ProductViewModel"
class ProductViewModel: ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product>
        get() = _product

    fun getProductList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.productService.getProductList()
                if(response.isSuccessful && response.body() != null){
                    _productList.postValue(response.body() as List<Product>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getProductList: ${e.message}")
            }
        }
    }

    fun getSelectProduct(productId: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.productService.getProduct(productId)
                if(response.isSuccessful && response.body() != null){
                    _product.postValue(response.body() as Product)
                }
            }catch (e: Exception){
                Log.d(TAG, "getSelectProduct: ${e.message}")
            }
        }
    }

    fun setProductItem(product: Product){
        _product.value = product
        Log.d(TAG, "onBindViewHolder: ${product.name}")
    }

    private val _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int>
        get() = _quantity
    fun setOrderQuantity(flag: Boolean){
        when(flag){
            // +
            true ->{
                _quantity.postValue((_quantity.value as Int) + 1)
            }
            // -
            false ->{
                if(_quantity.value as Int > 1)
                _quantity.postValue((_quantity.value as Int) - 1)
            }

        }
    }
}