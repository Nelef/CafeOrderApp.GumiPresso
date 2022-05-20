package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.RecentOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderService {
    @GET("/admin/order/completed")
    suspend fun getOrderListByCompleted(): Response<List<RecentOrder>>

    @GET("/admin/order/completed/{id}")
    suspend fun completeOrder(@Path("id") id: Int): Response<List<RecentOrder>>
}