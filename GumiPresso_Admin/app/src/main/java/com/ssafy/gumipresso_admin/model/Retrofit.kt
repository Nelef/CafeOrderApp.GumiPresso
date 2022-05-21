package com.ssafy.gumipresso_admin.model

import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.model.service.*

object Retrofit {
    val userService : UserService by lazy {
        ApplicationClass.retrofit.create(UserService::class.java)
    }
    val orderService: OrderService by lazy {
        ApplicationClass.retrofit.create(OrderService::class.java)
    }
    val salesService: SalesService by lazy {
        ApplicationClass.retrofit.create(SalesService::class.java)
    }
    val productService: ProductService by lazy {
        ApplicationClass.retrofit.create(ProductService::class.java)
    }

}