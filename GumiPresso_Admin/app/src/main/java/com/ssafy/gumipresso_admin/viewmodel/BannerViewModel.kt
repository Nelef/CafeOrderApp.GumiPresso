package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

private const val TAG ="BannerViewModel"
class BannerViewModel : ViewModel() {
    private val _bannerList = MutableLiveData<MutableList<Banner>>()
    val bannerList: LiveData<MutableList<Banner>>
        get() = _bannerList

    fun getBannerListItems(){
        Log.d(TAG, "getBannerListItems: ")
        viewModelScope.launch(Dispatchers.IO){
            try {                
                val response = Retrofit.bannerService.selectBanner()
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as MutableList<Banner>)
                    Log.d(TAG, "getBannerListItems: ${response.body()}")
                }
            }catch (e: Exception){
                Log.d(TAG, "getBannerListItems: ${e.message}")
            }
        }
    }

    private val _banner = MutableLiveData<Banner>()
    val banner: LiveData<Banner>
        get() = _banner
    fun setBannerItem(banner: Banner){
        _banner.value = banner
    }

    private val _isRegistTab = MutableLiveData(true)
    val isRegistTab: LiveData<Boolean>
        get() = _isRegistTab
    fun setIsRegistTabState(state: Boolean){
       _isRegistTab.value = state
    }

    fun insertBanner(banner: Banner, imageUrl: String){
        val file = File(imageUrl)
        var fileName = "banners/" + System.currentTimeMillis().toString()+".png"
        banner.img = fileName
        var requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var imageBody : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
        val json = Gson().toJson(banner)
        val bannerBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.bannerService.insertBanner(imageBody, bannerBody)
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as MutableList<Banner>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getBannerListItems: ${e.message}")
            }
        }
    }

    fun updateBanner(banner: Banner){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.bannerService.updateBanner(banner)
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as MutableList<Banner>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getBannerListItems: ${e.message}")
            }
        }
    }

    fun updateBannerImage(banner: Banner, imageUrl: String){
        val file = File(imageUrl)
        var fileName = "banners/" + System.currentTimeMillis().toString()+".png"
        var requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var imageBody : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
        val json = Gson().toJson(banner)
        val bannerBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.bannerService.updateBannerImage(imageBody, bannerBody)
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as MutableList<Banner>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getBannerListItems: ${e.message}")
            }
        }
    }

    fun deleteBanner(banner: Banner){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.bannerService.deleteBanner(banner)
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as MutableList<Banner>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getBannerListItems: ${e.message}")
            }
        }
    }

}
















