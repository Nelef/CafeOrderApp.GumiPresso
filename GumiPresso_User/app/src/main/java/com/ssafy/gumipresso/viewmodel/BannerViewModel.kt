package com.ssafy.gumipresso.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.Banner
import com.ssafy.gumipresso.model.dto.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "BannerViewModel"
class BannerViewModel : ViewModel() {
    private val _bannerList = MutableLiveData<List<Banner>>()
    val bannerList: LiveData<List<Banner>>
        get() = _bannerList

    private val _banner = MutableLiveData<Banner>()
    val banner : LiveData<Banner>
        get() = _banner

    fun clickBannerItem(position: Int) : String? {
        var address = _bannerList.value?.get(position)?.url.toString()
        Log.d(TAG, "clickBannerItem: {$address}")
        return address
    }

    fun getBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Retrofit.bannerService.getBannerList()
                if(response.isSuccessful && response.body() != null){
                    _bannerList.postValue(response.body() as List<Banner>)
                }
            }catch (e: Exception){
                Log.d(TAG, "getBanner: ${e.message}")
            }
        }
    }
}