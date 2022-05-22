package com.ssafy.gumipresso_admin.model.dto

import org.w3c.dom.Comment


data class Product(
    var name: String, val type: String,
    var price: Int, var img: String, val comment: ArrayList<Comment>? = ArrayList()){
    var id: Int? = 0
    constructor(): this("0", "", 0, "")
    constructor(id: Int, name: String, type: String, price: Int, img: String, comment: ArrayList<Comment>?): this(name, type, price, img, comment){
        this.id = id
    }


}