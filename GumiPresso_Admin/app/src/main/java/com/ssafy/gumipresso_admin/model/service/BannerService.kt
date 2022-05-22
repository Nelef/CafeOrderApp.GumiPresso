package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.model.dto.Banner
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface BannerService {
    @GET("/banner/")
    suspend fun selectBanner(): Response<List<Banner>>

    @Multipart
    @POST("/banner/")
    suspend fun insertBanner(@Part imageFile: MultipartBody.Part, @Part("banner") banner: RequestBody) : Response<List<Banner>>

    @PUT("/banner/")
    suspend fun updateBanner(@Body banner: Banner) : Response<List<Banner>>

    @Multipart
    @POST("/banner/image")
    suspend fun updateBannerImage(@Part imageFile: MultipartBody.Part, @Part("banner") banner: RequestBody) : Response<List<Banner>>

    @POST("/banner/delete")
    suspend fun deleteBanner(@Body banner: Banner): Response<List<Banner>>
}