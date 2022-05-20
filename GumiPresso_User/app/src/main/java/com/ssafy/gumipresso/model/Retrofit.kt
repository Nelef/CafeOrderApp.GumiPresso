package com.ssafy.gumipresso.model

import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.model.service.*

object Retrofit {
    val userService : UserService by lazy {
        ApplicationClass.retrofit.create(UserService::class.java)
    }
    val orderService: OrderService by lazy {
        ApplicationClass.retrofit.create(OrderService::class.java)
    }
    val productService: ProductService by lazy {
        ApplicationClass.retrofit.create(ProductService::class.java)
    }
    val commentService: CommentService by lazy {
        ApplicationClass.retrofit.create(CommentService::class.java)
    }
    val imageService: ImageService by lazy {
        ApplicationClass.retrofit.create(ImageService::class.java)
    }
    val tableService: TableService by lazy {
        ApplicationClass.retrofit.create(TableService::class.java)
    }
}