package com.ssafy.gumipresso_admin.model.dto

import java.io.Serializable

data class User(var id: String,var pass: String?): Serializable {
    var name = ""
    var stamps = 0
    var money = 0
    constructor(id: String, pass: String?, name: String, stamps: Int): this(id, pass){
        this.name = name
        this.stamps = stamps
    }

    constructor(id:String, pass: String?, name: String, stamps: Int, money: Int):this(id, pass, name, stamps){
        this.money = money
    }

    constructor(id: String, pass: String?, name: String):this(id, pass){
        this.name = name
    }
    override fun toString(): String {
        return "User(id='$id', pass='$pass', name='$name', stamps=$stamps)"
    }


}