package com.ssafy.gumipresso_admin.common

import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.fragment.LoginFragment

class ManageItems {
    companion object{
        private val ITEMS = listOf<Map<String, Any>>(
            mapOf("title" to "전체 메시지", "action" to R.id.action_manageFragment_to_pushMessageFragment),
            mapOf("title" to "메뉴 관리", "action" to R.id.action_manageFragment_to_menuEditFragment)
        )

        fun get(): List<Map<String, Any>>{
            return ITEMS
        }
    }
}