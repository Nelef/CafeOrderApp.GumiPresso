package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.tmap.ReceiveForm
import com.ssafy.gumipresso.model.dto.tmap.TMapDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TMapService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "appKey: l7xx4413ff598ab447188293f216e681c583"
    )
    @POST("prediction?resCoordType=WGS84GEO&reqCoordType=WGS84GEO&sort=index/")
    suspend fun getArrvalTimeInfo(@Body json: TMapDTO): Response<ReceiveForm>
}