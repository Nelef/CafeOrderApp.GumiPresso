package com.ssafy.gumipresso.model

import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.model.service.CommentService
import com.ssafy.gumipresso.model.service.OrderService
import com.ssafy.gumipresso.model.service.ProductService
import com.ssafy.gumipresso.model.service.UserService

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
}