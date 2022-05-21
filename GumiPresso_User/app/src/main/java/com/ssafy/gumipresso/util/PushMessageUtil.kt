package com.ssafy.gumipresso.util

import com.ssafy.gumipresso.common.ApplicationClass

class PushMessageUtil {

    fun setFcmToken(token: String){
        ApplicationClass.fcmTokenPrefs.edit().putString("Token", token).apply()
    }

    fun getFcmToken(): String{
        return ApplicationClass.fcmTokenPrefs.getString("Token","").toString()
    }


}