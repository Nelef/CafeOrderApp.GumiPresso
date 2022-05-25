package com.ssafy.gumipresso.viewmodel

import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.util.RSACryptUtil
import com.ssafy.gumipresso.util.SettingsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.math.BigInteger
import java.net.HttpURLConnection
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import kotlin.collections.HashMap

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

    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Retrofit.userService.logout()
                ApplicationClass.userPrefs.edit().clear().apply()
                ApplicationClass.cookiePrefs.edit().clear().apply()
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
        if(SettingsUtil().getPushStatePersonal()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    var map = mutableMapOf<String, String>()
                    map.put("token", token)
                    map.put("title", title)
                    map.put("content", content)
                    Retrofit.userService.sendFCMPushMessgae(map)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun updateMoney(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.userService.updateMoney(_user.value as User)
                if(response.isSuccessful && response.body() != null){
                    _user.postValue(response.body() as User)
                }
            }catch (e: Exception){
                Log.d(TAG, "orderCart: ${e.message}")
            }
        }
    }

    fun setUserMoney(totalPrice: Int){
        var user = _user.value as User
        user.money -= totalPrice
        _user.value = user
    }

    private var publicKey = ""
    fun getLoginRSAKey(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = Retrofit.userService.getLoginRSAKey()
                Log.d(TAG, "aosLogin: ${response}")
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    _user.postValue(response.body() as User)
                }else if(response.code() == 202){
                    publicKey = response.headers().get("publicKey").toString()
                }else{
                    Log.d(TAG, "aosLogin - FAIL: ${response}")
                }
            }catch (e: Exception){
                Log.d(TAG, "aosLogin error: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loginRSA(user: User){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val publicKey: PublicKey = RSACryptUtil().getPublicKeyFromBase64Encrypted(publicKey)
                val newUser = User(user.id, RSACryptUtil().encrypt(user.pass!!, publicKey))
                val response = Retrofit.userService.rsaLogin(newUser)
                if(response.isSuccessful){
                    _user.postValue(response.body() as User)
                    _loginSuccess.postValue(true)
                }
            }catch (e: Exception){
                Log.d(TAG, "loginRSA - Error: ${e.message}")
            }
            
        }
    }
}














