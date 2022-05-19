package com.ssafy.gumipresso_admin.model.service

import okhttp3.MultipartBody
import org.w3c.dom.Comment
import retrofit2.Response
import retrofit2.http.*

interface ImageService {
    @GET("/comment/{product_id}")
    suspend fun getComments(@Path("product_id") productId: Int): Response<MutableList<Comment>>

    @Multipart
    @POST("/image/upload")
    suspend fun insertImage(@Part imageFile: MultipartBody.Part): Response<Void>

}