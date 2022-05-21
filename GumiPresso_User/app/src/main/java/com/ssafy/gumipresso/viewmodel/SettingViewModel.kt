package com.ssafy.gumipresso.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.gumipresso.util.SettingsUtil

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

}