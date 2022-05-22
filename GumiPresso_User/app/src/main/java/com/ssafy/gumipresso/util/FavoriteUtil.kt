package com.ssafy.gumipresso.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.gumipresso.common.ApplicationClass

private const val TAG = "JSONConvertUtil"

class FavoriteUtil {
    companion object {
        private val sharedPreferences = ApplicationClass.favoritesPrefs
        private val typeToken = object : TypeToken<MutableList<String>>() {}.type

        fun getSharedPreferenceToList(): MutableList<String> {
            var favoritesList = Gson().toJson(mutableListOf<String>())
            favoritesList = sharedPreferences.getString("Favorite", favoritesList)
            val stringList: MutableList<String> = Gson().fromJson(favoritesList, typeToken)
            return stringList
        }

        fun setListToSharedPreference(list: MutableList<String>) {
            val favoritesList = Gson().toJson(list)
            sharedPreferences.edit().putString("Favorite", favoritesList).apply()
        }

        fun addToSharedPreference(msg: String) {
            var favoritesList = Gson().toJson(mutableListOf<String>())
            favoritesList = sharedPreferences.getString("Favorite", favoritesList)

            val stringList: MutableList<String> = Gson().fromJson(favoritesList, typeToken)
            stringList.add(msg)
            favoritesList = Gson().toJson(stringList)
            sharedPreferences.edit().putString("Favorite", favoritesList).apply()
        }
    }
}