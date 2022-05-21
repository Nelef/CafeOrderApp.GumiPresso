package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.model.Retrofit
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
        val type = "d"
        var requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
        val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
        val typeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), type)
        val map = mapOf<String, RequestBody>("name" to nameBody, "price" to priceBody, "type" to typeBody)

        viewModelScope.launch(Dispatchers.IO){
            try {
                Retrofit.imageService.insertProduct(body, map)
            }catch (e: Exception){
                Log.d(TAG, "insertProduct: ${e.message}")
            }
        }
    }

}