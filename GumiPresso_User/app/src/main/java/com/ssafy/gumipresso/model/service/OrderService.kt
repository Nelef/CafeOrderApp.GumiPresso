package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.OrderForm
import com.ssafy.gumipresso.model.dto.RecentOrder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("/order/recent/{userId}")
    suspend fun getOrderList(@Path("userId")user_id: String) : Response<List<RecentOrder>>

    @GET("/order/order/{orderId}")
    suspend fun getOrderDetailList(@Path("orderId") orderId: String): Response<RecentOrder>

    @POST("/order/new")
    suspend fun order(@Body orderForm: OrderForm): Response<Unit>
}