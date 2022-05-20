package com.ssafy.gumipresso.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.ssafy.gumipresso.model.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

private const val TAG = "ImageViewModel"
class ImageViewModel: ViewModel() {

    fun uploadImage(path : String){
        val file = File(path)
        var fileName = System.currentTimeMillis().toString()
        fileName = fileName+".png"

        Log.d(TAG, "uploadImage: ")

        var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)

        CoroutineScope(Dispatchers.IO).launch {
            Retrofit.imageService.insertImage(body)
        }
    }
}