package com.ssafy.gumipresso_admin.model.dto

import java.util.*

data class RecentOrder(var oId: Int, var orderTime: Date, var orderTable: String, var completed: String, var recentOrderDetail: List<OrderDetail>) {

}