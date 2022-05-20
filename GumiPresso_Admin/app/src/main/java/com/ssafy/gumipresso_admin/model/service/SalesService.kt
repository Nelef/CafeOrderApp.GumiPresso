package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.DateDTO
import com.ssafy.gumipresso_admin.model.dto.Sales
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SalesService {
    @POST("/admin/order/{format}")
    suspend fun getSales(@Path("format") format: String, @Body dateDTO: DateDTO): Response<List<Sales>>
}