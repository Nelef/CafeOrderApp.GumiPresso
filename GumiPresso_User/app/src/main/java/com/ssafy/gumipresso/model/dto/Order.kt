package com.ssafy.gumipresso.model.dto

import com.ssafy.gumipresso.util.DateFormatUtil

data class Order(var userId: String, var orderTable: String, var orderTime: String = DateFormatUtil.convertyyyyMMddHHmmss()) {
    var oId: Int = 0

    var completed: String = ""
    constructor(oId: Int, userId: String, orderTable: String, orderTime: String, completed: String): this(userId, orderTable, orderTime){
        this.oId = oId
    }
}