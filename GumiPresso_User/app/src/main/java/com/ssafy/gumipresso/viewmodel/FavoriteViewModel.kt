package com.ssafy.gumipresso.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.gumipresso.util.FavoriteUtil

private const val TAG = "FavoriteViewModel"
class FavoriteViewModel : ViewModel() {
    private val _favoriteList = MutableLiveData<MutableList<String>>()
    val favoriteList: LiveData<MutableList<String>>
        get() = _favoriteList

    fun getFavoriteList(){
        _favoriteList.postValue(FavoriteUtil.getSharedPreferenceToList())
    }

    fun deleteFavoriteList(name: String){
        _favoriteList.value?.remove(name)
    }
}