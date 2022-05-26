package com.ssafy.gumipresso.model.service

import com.kakao.sdk.auth.model.OAuthToken
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.model.dto.tmap.ReceiveForm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("/user/login")
    suspend fun login(@Body user: User): Response<User>

    @GET("/user/join/{id}")
    suspend fun getUsedId(@Path("id")id: String): Response<Boolean>

    @POST("/user/join")
    suspend fun join(@Body user: User): Response<User>

    @GET("/user/logout")
    suspend fun logout(): Response<Unit>

    @GET("/user/me")
    suspend fun getUser(): Response<User>

    @POST("/user/kakao")
    suspend fun sendKakaoToken(@Body token: String): Response<User>

    @POST("/user/naver")
    suspend fun sendNaverToken(@Body token: String): Response<User>

    @POST("/user/google")
    suspend fun googleLogin(@Body user: User): Response<User>

    @POST("/user/fcm")
    suspend fun sendFCMPushMessgae(@Body map: Map<String, String>) : Response<Void>

    @POST("/admin/fcm/insert")
    suspend fun insertFCMTokenUser(@Body map: Map<String, String>) : Response<Void>

    @POST("/admin/fcm/update")
    suspend fun updateFCMTokenUser(@Body map: Map<String, String>) : Response<Void>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "appKey: l7xx4413ff598ab447188293f216e681c583"
    )
    @POST("/{uri}")
    suspend fun getArrvalTimeInfo(@Path("uri", encoded = true) uri: String, @Body json: String): Response<ReceiveForm>

    @PUT("/user/money")
    suspend fun updateMoney(@Body user: User): Response<User>

    @GET("/user/aos")
    suspend fun getLoginRSAKey(): Response<Any>

    @POST("/user/aos")
    suspend fun rsaLogin(@Body user: User): Response<User>
}