package com.ssafy.gumipresso_amdin.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.gumipresso_admin.common.ApplicationClass

private const val TAG = "JSONConvertUtil"

class NoticeMessageUtil {
    companion object {
        private val sharedPreferences = ApplicationClass.noticePrefs
        private val typeToken = object : TypeToken<MutableList<String>>() {}.type

        fun getSharedPreferenceToList(): MutableList<String> {
            var jsonList = Gson().toJson(mutableListOf<String>())
            jsonList = sharedPreferences.getString("Message", jsonList)
            val stringList: MutableList<String> = Gson().fromJson(jsonList, typeToken)
            return stringList
        }

        fun setListToSharedPreference(list: MutableList<String>) {
            val jsonList = Gson().toJson(list)
            sharedPreferences.edit().putString("Message", jsonList).apply()
        }

        fun setMessageToSharedPreference(msg: String) {
            var jsonList = Gson().toJson(mutableListOf<String>())
            jsonList = sharedPreferences.getString("Message", jsonList)

            val stringList: MutableList<String> = Gson().fromJson(jsonList, typeToken)
            stringList.add(msg)
            jsonList = Gson().toJson(stringList)
            sharedPreferences.edit().putString("Message", jsonList).apply()
        }
    }
}