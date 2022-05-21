package com.ssafy.gumipresso_admin.model.dto

import org.w3c.dom.Comment


data class Product(val name: String, val type: String,
                   val price: Int, val img: String, val comment: ArrayList<Comment>? = ArrayList()){
    var id: Int? = 0
    constructor(): this("0", "", 0, "")
    constructor(id: Int, name: String, type: String, price: Int, img: String, comment: ArrayList<Comment>?): this(name, type, price, img, comment){
        this.id = id
    }


}