package com.ssafy.gumipresso.util

import com.ssafy.gumipresso.common.ApplicationClass

class SettingsUtil {

    fun setPushStateAll(accept: Boolean){
        ApplicationClass.pushStateAll.edit().putBoolean("State", accept).apply()
    }
    fun setPushStatePersonal(accept: Boolean){
        ApplicationClass.pushStatePersonal.edit().putBoolean("State", accept).apply()
    }
    fun getPushStateAll(): Boolean{
        return ApplicationClass.pushStateAll.getBoolean("State", true)
    }
    fun getPushStatePersonal(): Boolean{
        return ApplicationClass.pushStatePersonal.getBoolean("State", true)
    }

    fun setAutoLoginState(accept: Boolean){
        ApplicationClass.autoLoginState.edit().putBoolean("State", accept).apply()
    }
    fun getAutoLoginState(): Boolean{
        return ApplicationClass.autoLoginState.getBoolean("State", true)
    }
    fun getFirstRunCheck(): Boolean{
        return ApplicationClass.firstRunCheck.getBoolean("State", true)
    }
    fun setFirstRunCheck(accept: Boolean){
        ApplicationClass.firstRunCheck.edit().putBoolean("State", accept).apply()
    }
    fun setShakeToPayState(accept: Boolean){
        ApplicationClass.shakeToPayPrefs.edit().putBoolean("State", accept).apply()
    }
    fun getShakeToPayState(): Boolean{
        return ApplicationClass.shakeToPayPrefs.getBoolean("State", true)
    }

}