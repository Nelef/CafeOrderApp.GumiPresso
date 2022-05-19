package com.ssafy.gumipresso.model.dto

data class RecentOrderDetail(var productId: Int, var quantity: Int, var name: String, var type: String, var price: Int, var img: String) {
}