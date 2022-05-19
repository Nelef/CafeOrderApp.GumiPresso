package com.ssafy.gumipresso_admin.common.interceptor

import com.ssafy.gumipresso_amdin.util.CookieUtil
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val cookies = CookieUtil().getCookies()
        for(cookie in cookies!!){
            builder.addHeader("Cookie",cookie)
        }

        builder.removeHeader("User-Agent").addHeader("User-Agent","Android")
        return chain.proceed(builder.build())
    }
}