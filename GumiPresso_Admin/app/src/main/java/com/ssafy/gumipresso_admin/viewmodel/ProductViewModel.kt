package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody

import okhttp3.RequestBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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

    private val _flagEdit = MutableLiveData<Boolean>(false)
    val flagEdit : LiveData<Boolean>
        get() = _flagEdit
    fun setFlagState(state: Boolean){
        _flagEdit.value = state
    }

    private val _flagImageChange = MutableLiveData(false)
    val flagImageChange: LiveData<Boolean>
        get() = _flagImageChange
    fun setFlagImageItemChange(state: Boolean){
        _flagImageChange.value = state
    }



    fun insertProduct(name: String, price: Int, type: String, imageUrl: String) {
        val file = File(imageUrl)
        var fileName = System.currentTimeMillis().toString()
        fileName = fileName+".png"

        var requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
        val json = Gson().toJson(Product(name,type,price,fileName))
        val mapbody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        viewModelScope.launch(Dispatchers.IO){
            try {
                Retrofit.productService.insertProduct(body, mapbody)
            }catch (e: Exception){
                Log.d(TAG, "insertProduct: ${e.message}")
            }
        }
    }

    fun updateProduct(product: Product, imageUrl: String?){
        if(imageUrl != null){
            val file = File(imageUrl)
            var fileName = System.currentTimeMillis().toString()+".png"
            var requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
            val json = Gson().toJson(product)
            val productBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
            viewModelScope.launch(Dispatchers.IO){
                try {
                    Retrofit.productService.updateProductImage(body, productBody)
                }catch (e: Exception){
                    Log.d(TAG, "insertProduct: ${e.message}")
                }
            }
        }else{
            viewModelScope.launch(Dispatchers.IO){
                try {
                    Retrofit.productService.updateProduct(product)
                }catch (e: Exception){
                    Log.d(TAG, "insertProduct: ${e.message}")
                }
            }
        }
    }

    fun deleteProduct(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                Retrofit.productService.deleteProduct(id)
            }catch (e: Exception){
                Log.d(TAG, "insertProduct: ${e.message}")
            }
        }
    }

}