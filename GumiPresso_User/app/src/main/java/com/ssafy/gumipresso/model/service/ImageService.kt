package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.Comment
import com.ssafy.gumipresso.model.dto.ImageFile
import com.ssafy.gumipresso.model.dto.Product
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ImageService {
    @GET("/comment/{product_id}")
    suspend fun getComments(@Path("product_id") productId: Int): Response<MutableList<Comment>>

    @Multipart
    @POST("/image/upload")
    suspend fun insertImage(@Part imageFile: MultipartBody.Part, @Part("product") product: Product): Response<Void>

}