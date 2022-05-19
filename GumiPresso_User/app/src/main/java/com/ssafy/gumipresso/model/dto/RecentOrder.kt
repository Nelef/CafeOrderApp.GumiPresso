package com.ssafy.gumipresso.model.dto

import java.util.*

data class RecentOrder(var oId: Int, var orderTime: Date, var recentOrderDetail: List<RecentOrderDetail>) {

}