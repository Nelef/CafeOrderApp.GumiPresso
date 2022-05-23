package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.Banner
import retrofit2.Response
import retrofit2.http.GET

interface BannerService {
    @GET("/banner/")
    suspend fun getBannerList(): Response<List<Banner>>
}