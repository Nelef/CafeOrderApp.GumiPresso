package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("/admin/login")
    suspend fun loginAdmin(@Body user: User): Response<User>

    @GET("/admin/me")
    suspend fun getAdminUser(): Response<User>

    @GET("/admin/logout")
    suspend fun logout(): Response<Unit>

    @GET("/admin/join/{id}")
    suspend fun checkId(@Path("id")id: String): Response<Boolean>

    @POST("/admin/join")
    suspend fun insertAdmin(@Body user: User): Response<User>

    @POST("/admin/fcm")
    suspend fun sendFCMPushMessgae(@Body map: Map<String, String>) : Response<Void>

    @POST("/user/{user_id}")
    suspend fun getPayUser(@Path("user_id")userId: String): Response<User>

    @PUT("/user/money")
    suspend fun updateUserMoney(@Body user: User): Response<Void>

    @GET("/user/aos")
    suspend fun getPublicKey(): Response<User>
}