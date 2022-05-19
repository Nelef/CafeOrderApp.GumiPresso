package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.gumipresso.util.NoticeMessageUtil

private const val TAG ="NoticeViewModel"
class NoticeViewModel: ViewModel() {
    private val _notice = MutableLiveData<MutableList<String>>()
    val notice: LiveData<MutableList<String>>
        get() = _notice

    fun getNoticeList(){
        _notice.postValue(NoticeMessageUtil.getSharedPreferenceToList())
    }

    fun deleteNotice(position: Int){
        _notice.value?.removeAt(position)
        Log.d(TAG, "deleteNotice: ${_notice.value as MutableList<String>}")
    }
}