package com.ssafy.gumipresso_admin.model

import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.model.service.UserService

object Retrofit {
    val userService : UserService by lazy {
        ApplicationClass.retrofit.create(UserService::class.java)
    }
}