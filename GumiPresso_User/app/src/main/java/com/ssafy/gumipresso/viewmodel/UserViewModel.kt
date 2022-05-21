package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.util.SettingsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG ="UserViewModel"
class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    private val _loginSuccess = MutableLiveData<Boolean>()
    val logdinSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun login(user: User){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.login(user)
                if(response.isSuccessful && response.body() != null){
                    _user.postValue((response.body() as User))
                    _loginSuccess.postValue(true)
                    Log.d(TAG, "login: ${response.headers()}")
                }
                else if(response.body() == null){
                    _loginSuccess.postValue(false)
                }
            }catch (e: Exception){
                Log.d(TAG, "login: ${e.message}")
                _loginSuccess.postValue(false)
            }
        }
    }

    private val _userId = MutableLiveData<String>("")
    val userId : LiveData<String>
        get() = _userId
    fun setUserIdBind(userId: String){
        _userId.value = userId
    }

    private val _isUsedId = MutableLiveData<Boolean>()
    val isUsedId: LiveData<Boolean>
        get() = _isUsedId

    fun getUsedId(id: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.getUsedId(id)
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
                val response = Retrofit.userService.join(user)
                _user.postValue(response.body() as User)
            }catch (e: Exception){
                Log.d(TAG, "join: ${e.message}")
            }
        }
    }

    fun getUserInfo(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.getUser()
                if(response.isSuccessful){
                    _user.postValue(response.body() as User)
                    _loginSuccess.postValue(true)
                }
                else{
                    _loginSuccess.postValue(false)
                }
            }catch (e: Exception){
                Log.d(TAG, "getUser: ${e.message}")
            }
        }
    }

    fun sendKakaoToken(token: String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = Retrofit.userService.sendKakaoToken(token)
                if(response.isSuccessful && response.body() != null){
                    _user.postValue(response.body() as User)
                    _loginSuccess.postValue(true)
                }
                else{
                    _loginSuccess.postValue(false)
                }

            }catch (e: Exception){
                Log.d(TAG, "sendToken: ${e.message}")
            }
        }
    }

    fun sendNaverToken(token: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.sendNaverToken(token)
                if(response.isSuccessful){
                    _user.postValue(response.body() as User)
                    _loginSuccess.postValue(true)
                }

            }catch (e: Exception){
                Log.d(TAG, "sendNaverToken: ${e.message}")
            }
        }
    }

    fun sendFCMPushMessage(token: String, title: String, content: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                var map = mutableMapOf<String, String>()
                map.put("token", token)
                map.put("title", title)
                map.put("content", content)
                Retrofit.userService.sendFCMPushMessgae(map)
            }catch (e: Exception){

            }
        }
    }

}