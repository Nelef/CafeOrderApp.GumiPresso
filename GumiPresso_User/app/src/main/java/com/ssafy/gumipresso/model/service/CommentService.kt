package com.ssafy.gumipresso.model.service

import com.ssafy.gumipresso.model.dto.Comment
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CommentService {
    @GET("/comment/{product_id}")
    suspend fun getComments(@Path("product_id") productId: Int): Response<MutableList<Comment>>

    @POST("/comment/")
    suspend fun insertComment(@Body comment: Comment): Response<MutableList<Comment>>

    @PUT("/comment/")
    suspend fun updateComment(@Body comment: Comment): Response<MutableList<Comment>>

    @DELETE("/comment/{id}")
    suspend fun deleteComment(@Path("id")id: Int): Response<MutableList<Comment>>

    @POST("/comment/image")
    suspend fun insertCommentImage(@Part imageFile: MultipartBody.Part, @Part("product") product: RequestBody): Response<MutableList<Comment>>
}