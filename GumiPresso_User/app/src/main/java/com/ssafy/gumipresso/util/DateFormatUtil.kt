package com.ssafy.gumipresso.util

import android.os.Build
import androidx.annotation.RequiresApi
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
        fun convertyyyyMMddTHHmmsszz(): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            return formatter.format(System.currentTimeMillis())
        }

        fun convertTMapArrivalTime(arrvalTime: String): String{
            var formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            val date = formatter.parse(arrvalTime)
            formatter = SimpleDateFormat("HH:mm")
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")

            return formatter.format(date)
        }

        fun convertTMapTotalTime(totalTime: Int): String{
            var hour = totalTime / 3600
            var minute = totalTime / 60
            var second = totalTime % 60
            if(hour > 0){
                return "${hour}시간 ${minute}분"
            }else{
                return "${minute}분 ${second}초"
            }
        }
    }
}