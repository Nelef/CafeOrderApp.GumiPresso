package com.ssafy.gumipresso_amdin.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.gumipresso_admin.model.dto.Sales
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatUtil {
    companion object {
        fun convertYYMMDD(date: Date): String {
            val formatter = SimpleDateFormat("yy-MM-dd")
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            return formatter.format(date)
        }

        fun convertYYMMDDHHMM(date: Date): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            return formatter.format(date)
        }


        fun convertyyyyMMddHHmmss(): String{
            val formmater: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            formmater.timeZone = TimeZone.getTimeZone("Asia/Seoul")

            return formmater.format(System.currentTimeMillis())
        }
        fun convertSalesData(sales: Sales): String{
            var date = "${sales.year}년"
            if(sales.month != null) date += " ${sales.month}월"
            if(sales.day != null) date += " ${sales.day}일"
            if(sales.hour != null) date += " ${sales.hour}시"
            return date
        }
    }
}