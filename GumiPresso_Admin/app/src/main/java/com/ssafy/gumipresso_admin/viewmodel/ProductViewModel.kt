package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
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
            var fileName = product.img
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

    fun deleteProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            try {
                Retrofit.productService.deleteProduct(product)
            }catch (e: Exception){
                Log.d(TAG, "insertProduct: ${e.message}")
            }
        }
    }

}