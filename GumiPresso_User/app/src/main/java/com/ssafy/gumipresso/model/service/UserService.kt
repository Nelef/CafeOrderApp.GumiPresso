package com.ssafy.gumipresso.model.service

import com.kakao.sdk.auth.model.OAuthToken
import com.ssafy.gumipresso.model.dto.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("/user/login")
    suspend fun login(@Body user: User): Response<User>

    @GET("/user/join/{id}")
    suspend fun getUsedId(@Path("id")id: String): Response<Boolean>

    @POST("/user/join")
    suspend fun join(@Body user: User): Response<User>

    @GET("/user/me")
    suspend fun getUser(): Response<User>

    @POST("/user/kakao")
    suspend fun sendKakaoToken(@Body token: String): Response<User>

    @POST("/user/naver")
    suspend fun sendNaverToken(@Body token: String): Response<User>
}