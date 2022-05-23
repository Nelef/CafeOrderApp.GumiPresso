package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("/admin/login")
    suspend fun loginAdmin(@Body user: User): Response<User>

    @GET("/admin/me")
    suspend fun getAdminUser(): Response<User>

    @GET("/admin/join/{id}")
    suspend fun checkId(@Path("id")id: String): Response<Boolean>

    @POST("/admin/join")
    suspend fun insertAdmin(@Body user: User): Response<User>

    @POST("/admin/fcm")
    suspend fun sendFCMPushMessgae(@Body map: Map<String, String>) : Response<Void>

    @PUT("/user/money")
    suspend fun updateUserMoney(@Body user: User): Response<Void>
}