package com.ssafy.gumipresso_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.model.dto.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="PayViewModel"
class PayViewModel: ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user
    fun getUserByQRCode(userId: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.getPayUser(userId)
                if(response.isSuccessful && response.body() != null){
                    _user.postValue(response.body() as User)
                }
            }catch (e : Exception){
                Log.d(TAG, "getUserByQRCode: ${e.message}")
            }
        }
    }
    fun updatePayMoney(user: User){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.updateUserMoney(user)
                if (response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body() as User)
                }
            }catch (e: Exception){
                Log.d(TAG, "updatePayMoney: ${e.message}")
            }
        }
    }

    private val _money = MutableLiveData<Int>(0)
    val money: LiveData<Int>
        get() = _money
    fun addMoney(money: Int){
        _money.value = _money.value?.plus(money)
    }
    fun clearMoney(){
        _money.value = 0
    }
}