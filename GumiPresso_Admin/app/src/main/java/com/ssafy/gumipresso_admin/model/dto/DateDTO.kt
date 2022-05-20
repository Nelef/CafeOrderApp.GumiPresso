package com.ssafy.gumipresso_admin.model.dto

data class DateDTO(var year: String, var month: String, var day: String) {
    override fun toString(): String {
        return "${year}년 ${month}월 ${day}일"
    }
}