package com.ssafy.gumipresso.model.dto

data class AosOrderDetail(var productId: Int, var quantity: Int) {
    var dId: Int = 0;
    var orderId: Int = 0;
    constructor(dId: Int, orderId: Int, productId: Int, quantity: Int): this(productId, quantity)
}