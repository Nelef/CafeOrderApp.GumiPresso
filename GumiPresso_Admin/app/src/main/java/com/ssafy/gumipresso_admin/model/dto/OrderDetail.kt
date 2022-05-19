package com.ssafy.gumipresso_admin.model.dto

data class OrderDetail(var productId: Int, var quantity: Int, var name: String, var type: String, var price: Int, var img: String) {
}