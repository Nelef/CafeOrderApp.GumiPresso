package com.ssafy.gumipresso.model.dto

import java.io.Serializable

data class User(var id: String,var pass: String?): Serializable {
    var name = ""
    var money = 0
    var stamps = 0
    constructor(id: String, pass: String?, name: String, stamps: Int, money:Int): this(id, pass){
        this.name = name
        this.money = money
        this.stamps = stamps
    }
    override fun toString(): String {
        return "User(id='$id', pass='$pass', name='$name', stamps=$stamps, money=$money)"
    }


}