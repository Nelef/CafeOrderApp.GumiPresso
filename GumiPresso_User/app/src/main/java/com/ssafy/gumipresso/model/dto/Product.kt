package com.ssafy.gumipresso.model.dto

import java.io.Serializable


data class Product(val id: Int?, val name: String, val type: String,
                   val price: Int, val img: String, val comment: ArrayList<Comment>? = ArrayList()): Serializable{
    constructor(): this(0, "", "", 0, "")

}