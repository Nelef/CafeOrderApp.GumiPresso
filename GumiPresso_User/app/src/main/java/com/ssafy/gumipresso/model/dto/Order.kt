package com.ssafy.gumipresso.model.dto

import com.ssafy.gumipresso.util.DateFormatUtil

data class Order(var userId: String, var orderTable: String, var orderTime: String = DateFormatUtil.convertyyyyMMddHHmmss()) {
    var oId: Int = 0
    var arrival_time: String? = "도착 시간 정보가 없습니다."
    var remain_time: String? = "도착 시간 정보가 없습니다."

    var completed: String = ""
    constructor(oId: Int, userId: String, orderTable: String, orderTime: String, completed: String): this(userId, orderTable, orderTime){
        this.oId = oId
    }

    constructor(oId: Int,userId: String,orderTable: String,orderTime: String,completed: String,arrival_time: String?, remain_time: String?):this(oId, userId, orderTable, orderTime, completed){
        this.arrival_time = arrival_time
        this.remain_time = remain_time
    }
}