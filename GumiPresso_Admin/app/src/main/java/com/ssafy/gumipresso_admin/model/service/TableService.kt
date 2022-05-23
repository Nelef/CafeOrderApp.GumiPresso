package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.Table
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TableService {
    @GET("/order/table")
    suspend fun getOrdertableList(): Response<List<Table>>

    @GET("/order/table/{tableId}")
    suspend fun setOrdertable(@Path("tableId") tableId: Int): Response<List<Table>>
}