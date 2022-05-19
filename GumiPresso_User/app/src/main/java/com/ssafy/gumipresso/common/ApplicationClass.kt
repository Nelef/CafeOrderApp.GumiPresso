package com.ssafy.gumipresso.common

import android.app.Application
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.common.interceptor.AddCookiesInterceptor
import com.ssafy.gumipresso.common.interceptor.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass: Application() {
    private val SERVER_URL = "http://ssafymobile.iptime.org:7890"
    companion object{
        lateinit var retrofit: Retrofit
        lateinit var okHttp: OkHttpClient
        lateinit var userPrefs : SharedPreferences
        lateinit var noticePrefs : SharedPreferences
        lateinit var cookiePrefs : SharedPreferences

    }
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_api_key))
        NaverIdLoginSDK.initialize(this, "1uV2k7noXRSfAiWm8pNl", "t6w7fTzU5G", "GumiPresso")

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
    }
}