package com.ssafy.gumipresso.common

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.common.interceptor.AddCookiesInterceptor
import com.ssafy.gumipresso.common.interceptor.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass: Application() {
    private val SERVER_URL = "https://gumipresso-back.imoneleft.synology.me"
    companion object{
        lateinit var retrofit: Retrofit
        lateinit var okHttp: OkHttpClient
        lateinit var userPrefs : SharedPreferences
        lateinit var noticePrefs : SharedPreferences
        lateinit var cookiePrefs : SharedPreferences
        lateinit var fcmTokenPrefs: SharedPreferences
        lateinit var pushStateAll: SharedPreferences
        lateinit var pushStatePersonal: SharedPreferences
        lateinit var autoLoginState: SharedPreferences
        lateinit var firstRunCheck: SharedPreferences
        lateinit var favoritesPrefs: SharedPreferences
        lateinit var shakeToPayPrefs: SharedPreferences
    }
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_api_key))
        NaverIdLoginSDK.initialize(this, "1uV2k7noXRSfAiWm8pNl", "t6w7fTzU5G", "GumiPresso")

        okHttp = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .build()

        var gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        userPrefs = getSharedPreferences("User", MODE_PRIVATE)
        noticePrefs = getSharedPreferences("CloudMessage", MODE_PRIVATE)
        cookiePrefs = getSharedPreferences("Cookie", MODE_PRIVATE)
        fcmTokenPrefs = getSharedPreferences("FcmToken", MODE_PRIVATE)
        pushStateAll = getSharedPreferences("PushStateAll", MODE_PRIVATE)
        pushStatePersonal = getSharedPreferences("PushStatePersonal", MODE_PRIVATE)
        autoLoginState = getSharedPreferences("AutoLogin", MODE_PRIVATE)
        firstRunCheck = getSharedPreferences("FirstRun", MODE_PRIVATE)
        favoritesPrefs = getSharedPreferences("favorite", MODE_PRIVATE)
        shakeToPayPrefs = getSharedPreferences("ShakeToPay", MODE_PRIVATE)
    }
}