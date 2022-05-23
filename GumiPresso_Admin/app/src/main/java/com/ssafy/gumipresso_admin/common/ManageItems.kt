package com.ssafy.gumipresso_admin.common

import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.fragment.LoginFragment

class ManageItems {
    companion object{
        private val ITEMS = listOf<Map<String, Any>>(
            mapOf("title" to "전체 메시지", "action" to R.id.action_manageFragment_to_pushMessageFragment),
            mapOf("title" to "메뉴 관리", "action" to R.id.action_manageFragment_to_menuEditFragment),
            mapOf("title" to "배너 관리", "action" to R.id.action_manageFragment_to_bannerFragment),
            mapOf("title" to "테이블 관리", "action" to R.id.action_manageFragment_to_tableFragment),
            mapOf("title" to "페이 관리", "action" to R.id.action_manageFragment_to_payManageFragment)

        )

        fun get(): List<Map<String, Any>>{
            return ITEMS
        }
    }
}