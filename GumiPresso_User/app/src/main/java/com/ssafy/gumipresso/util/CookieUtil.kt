package com.ssafy.gumipresso.util

import android.util.Log
import com.ssafy.gumipresso.common.ApplicationClass

private const val TAG ="CookieUtil"
class CookieUtil {
    private val sharedPreferences = ApplicationClass.cookiePrefs
    fun setCookies(cookies: HashSet<String>){
        Log.d(TAG, "setCookies: ${cookies}")
        sharedPreferences.edit().putStringSet("Cookie", cookies).apply()
    }
    fun getCookies(): MutableSet<String>?{
        Log.d(TAG, "getCookies: ${sharedPreferences.getStringSet("Cookie", HashSet())}")
        return sharedPreferences.getStringSet("Cookie", HashSet())
    }
    fun removeCookies(){
        sharedPreferences.edit().remove("Cookie").apply()
    }
}