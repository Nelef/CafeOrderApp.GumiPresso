package com.ssafy.gumipresso_admin.model.dto

import java.util.*

data class RecentOrder(var oId: Int, var orderTime: Date, var orderTable: String, var completed: String, var recentOrderDetail: List<OrderDetail>) {
    var user_id: String? = null
    var arrival_time: String? = null
    var remain_time: String? = null
    constructor(oId: Int, user_id: String?, orderTime: Date, orderTable: String, completed: String, arrival_time: String?, remain_time: String?, recentOrderDetail: List<OrderDetail>):this(oId, orderTime, orderTable, completed, recentOrderDetail){
        this.user_id = user_id
        this.arrival_time = arrival_time
        this.remain_time = remain_time
    }

    override fun toString(): String {
        return "${super.toString()}  ${this.arrival_time} ${this.remain_time} ${this.completed}"
    }
}