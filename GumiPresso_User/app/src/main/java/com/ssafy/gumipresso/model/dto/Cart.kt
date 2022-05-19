package com.ssafy.gumipresso.model.dto

data class Cart(val menuId: Int, val menuImg: String,
                val menuName: String,var menuCnt: Int,
                var menuPrice: Int, var totalPrice: Int = menuCnt*menuPrice,
                val type: String){

    fun addCartItem(cart: Cart){
        this.menuCnt += cart.menuCnt
        this.totalPrice = this.menuCnt * this.menuPrice
    }
}