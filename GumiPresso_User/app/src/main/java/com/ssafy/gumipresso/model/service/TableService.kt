package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.Table
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TableService {
    @GET("/order/table")
    suspend fun getOrdertableList(): Response<Table>

    @GET("/order/table/{tableId}")
    suspend fun setOrdertable(@Path("tableId") tableId: Int): Response<Table>
}