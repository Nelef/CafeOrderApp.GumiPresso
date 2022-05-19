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

private const val TAG ="UserViewModel"
class UserViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _loginSuccess = MutableLiveData<Boolean>()
    val logdinSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun login(user: User){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.loginAdmin(user)
                if(response.isSuccessful && response.body() != null){
                    _user.postValue((response.body() as User))
                    _loginSuccess.postValue(true)
                }
                else if(response.body() == null){
                    _loginSuccess.postValue(false)
                }
            }catch (e: Exception){
                _loginSuccess.postValue(false)
                Log.d(TAG, "login: ${e.message}")
            }
        }
    }

    private val _isUsedId = MutableLiveData<Boolean>()
    val isUsedId: LiveData<Boolean>
        get() = _isUsedId

    fun getUsedId(id: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.checkId(id)
                if(response.isSuccessful &&response.body() == true){
                    _isUsedId.postValue(true)
                }
                else if(response.body() == false){
                    _isUsedId.postValue(false)
                }
            }catch (e: Exception){
                Log.d(TAG, "checkID: ${e.message}")
            }
        }
    }

    fun join(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Retrofit.userService.insertAdmin(user)
                _user.postValue(response.body() as User)
            }catch (e: Exception){
                Log.d(TAG, "join: ${e.message}")
            }
        }
    }
}