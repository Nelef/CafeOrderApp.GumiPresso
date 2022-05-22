package com.ssafy.gumipresso.util

import android.util.Log
import com.ssafy.gumipresso.common.ApplicationClass

private const val TAG ="CookieUtil"
class CookieUtil {
    private val sharedPreferences = ApplicationClass.cookiePrefs
    fun setCookies(cookies: HashSet<String>){
        sharedPreferences.edit().putStringSet("Cookie", cookies).apply()
    }
    fun getCookies(): MutableSet<String>?{
        return sharedPreferences.getStringSet("Cookie", HashSet())
    }
    fun removeCookies(){
        sharedPreferences.edit().remove("Cookie").apply()
    }
}