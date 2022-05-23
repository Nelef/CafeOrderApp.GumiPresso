package com.ssafy.gumipresso.model.dto

import java.util.*

data class RecentOrder(var oId: Int, var orderTime: Date, var recentOrderDetail: List<RecentOrderDetail>) {
    var orderTable: String? = null
    var completed: String? = null
    constructor(oId: Int, orderTime: Date, orderTable: String?, completed: String?, recentOrderDetail: List<RecentOrderDetail>): this(oId, orderTime, recentOrderDetail){
        this.orderTable = orderTable
        this.completed = completed
    }
}