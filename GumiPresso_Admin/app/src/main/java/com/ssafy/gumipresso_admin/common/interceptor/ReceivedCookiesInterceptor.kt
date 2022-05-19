package com.ssafy.gumipresso_admin.common.interceptor

import com.ssafy.gumipresso_amdin.util.CookieUtil
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG ="ReceivedCookiesInterceptor"
class ReceivedCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if(originalResponse.header("Set-Cookie")?.isNotEmpty() == true){
            val cookies = HashSet<String>()
            for( header in originalResponse.headers("Set-Cookie")){
                cookies.add(header)
            }
            CookieUtil().setCookies(cookies)

        }
        return originalResponse
    }
}