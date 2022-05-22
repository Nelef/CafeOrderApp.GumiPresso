package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("/product/")
    suspend fun getProductList(): Response<List<Product>>

    @GET("/product/select/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: String): Response<Product>

    @GET("/product/rating")
    suspend fun selectProductOrderByRating(): Response<List<Product>>

    @GET("/product/quantity")
    suspend fun selectProductOrderByQuantity(): Response<List<Product>>
}