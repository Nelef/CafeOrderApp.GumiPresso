package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.util.CookieUtil
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.util.SettingsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="SettingViewModel"
class SettingViewModel: ViewModel() {
    private val _pushStateAll = MutableLiveData<Boolean>()
    val pushStateAll: LiveData<Boolean>
        get() = _pushStateAll
    private val _pushStatePersonal = MutableLiveData<Boolean>()
    val pushStatePersonal: LiveData<Boolean>
        get() = _pushStatePersonal
    fun setPushAll(){
        _pushStateAll.value = !_pushStateAll.value!!
        SettingsUtil().setPushStateAll(_pushStateAll.value!!)
        updateFCMToken()
    }
    fun setPushPersonal(){
        _pushStatePersonal.value = !_pushStatePersonal.value!!
        SettingsUtil().setPushStatePersonal(_pushStatePersonal.value!!)
    }
    fun getPushState(){
        _pushStateAll.value = SettingsUtil().getPushStateAll()
        _pushStatePersonal.value = SettingsUtil().getPushStatePersonal()
    }

    private val _autoLoginState = MutableLiveData<Boolean>()
    val autoLoginState : LiveData<Boolean>
        get() = _autoLoginState
    fun setAutoLogin(){
        _autoLoginState.value = !_autoLoginState.value!!
        SettingsUtil().setAutoLoginState(_autoLoginState.value!!)
    }
    fun getAutoLogin(){
        _autoLoginState.value = SettingsUtil().getAutoLoginState()
    }

    fun insertFCMToken(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val userId = ApplicationClass.userPrefs.getString("User","").toString()
                val token = PushMessageUtil().getFcmToken()
                val state = SettingsUtil().getPushStateAll()
                val map = mapOf<String,String>("userId" to userId, "token" to token, "state" to state.toString())
                Retrofit.userService.insertFCMTokenUser(map)
            }catch (e: Exception){
                Log.d(TAG, "insertFCMToken: ${e.message}")
            }
        }
    }

    fun updateFCMToken(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val userId = ApplicationClass.userPrefs.getString("User","").toString()
                val token = PushMessageUtil().getFcmToken()
                val state = SettingsUtil().getPushStateAll()
                val map = mapOf<String,String>("userId" to userId, "token" to token, "state" to state.toString())
                Retrofit.userService.updateFCMTokenUser(map)
            }catch (e: Exception){
                Log.d(TAG, "insertFCMToken: ${e.message}")
            }
        }
    }
}