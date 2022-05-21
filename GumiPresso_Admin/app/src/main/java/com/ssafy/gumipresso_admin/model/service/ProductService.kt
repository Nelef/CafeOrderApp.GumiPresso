package com.ssafy.gumipresso_admin.model.service

import com.ssafy.gumipresso_admin.model.dto.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.w3c.dom.Comment
import retrofit2.Response
import retrofit2.http.*

interface ProductService {
    @Multipart
    @POST("admin/product")
    suspend fun insertProduct(@Part imageFile: MultipartBody.Part, @Part("product") product: RequestBody): Response<Void>

    @Multipart
    @PUT("admin/product/image")
    suspend fun updateProductImage(@Part imageFile: MultipartBody.Part, @Part("product") product: RequestBody): Response<Void>

    @PUT("admin/product")
    suspend fun updateProduct(@Body product: Product): Response<Void>

    @DELETE("admin/product/{id}")
    suspend fun deleteProduct(@Path("id")id: Int): Response<Void>

    @GET("/product/")
    suspend fun getProductList(): Response<List<Product>>

    @GET("/product/select/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: String): Response<Product>


}