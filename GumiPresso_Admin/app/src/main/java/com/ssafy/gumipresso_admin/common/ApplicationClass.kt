package com.ssafy.gumipresso_admin.common

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.ssafy.gumipresso_admin.common.interceptor.AddCookiesInterceptor
import com.ssafy.gumipresso_admin.common.interceptor.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class ApplicationClass: Application() {
    private val SERVER_URL = "https://gumipresso-back.imoneleft.synology.me"
    companion object{
        lateinit var retrofit: Retrofit
        lateinit var okHttp: OkHttpClient
        lateinit var userPrefs : SharedPreferences
        lateinit var noticePrefs : SharedPreferences
        lateinit var cookiePrefs : SharedPreferences
        lateinit var fcmTokenPrefs: SharedPreferences

    }
    override fun onCreate() {
        super.onCreate()
        okHttp = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userPrefs = getSharedPreferences("User", MODE_PRIVATE)
        noticePrefs = getSharedPreferences("CloudMessage", MODE_PRIVATE)
        cookiePrefs = getSharedPreferences("Cookie", MODE_PRIVATE)
        fcmTokenPrefs = getSharedPreferences("FcmToken", MODE_PRIVATE)
    }
}