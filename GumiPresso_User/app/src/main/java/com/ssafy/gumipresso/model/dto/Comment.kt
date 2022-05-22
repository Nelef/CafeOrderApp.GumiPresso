package com.ssafy.gumipresso.model.dto

import java.io.Serializable

data class Comment (var userId: String, var productId :Int, var rating: Float, var comment: String, var img: String?): Serializable {
    var id :Int = 0
    constructor(id: Int, user_id: String, product_id: Int, rating: Float, comment: String, img: String?): this(user_id, product_id, rating, comment, img)
}