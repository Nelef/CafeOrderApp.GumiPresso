package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.gumipresso.model.Retrofit
import com.ssafy.gumipresso.model.dto.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

private const val TAG ="CommentViewModel"
class CommentViewModel: ViewModel() {
    private val _commentList = MutableLiveData<MutableList<Comment>>(mutableListOf())
    val commentList : LiveData<MutableList<Comment>>
        get() = _commentList


    private val _comment = MutableLiveData<Comment>()
    val comment : LiveData<Comment>
        get() = _comment

    fun setCommentItem(comment: Comment){
        _comment.value = comment
    }

    fun getComments(productId: Int){
        viewModelScope.launch(Dispatchers.Main){
            try {
                val response = Retrofit.commentService.getComments(productId)
                if(response.isSuccessful && response.body() != null){
                    _commentList.value = (response.body() as MutableList<Comment>)
                }
                else{
                    Log.d(TAG, "getComments: ${response}")
                }
            }
            catch (e: Exception){
                Log.d(TAG, "getComments: ${e.message}")
            }
        }
    }

    private val _avgRating = MutableLiveData<Float>(0f)
    val avgRating: LiveData<Float>
        get() = _avgRating
    fun setAverageRating(){
        val list = _commentList.value as List<Comment>
        var rating = 0f
        for(i in list.indices){
            rating += list[i].rating
        }
        _avgRating.postValue(rating / list.size)
    }

    private val _isImageUpload = MutableLiveData<Boolean>(false)
    val isImageUpload : LiveData<Boolean>
        get() = _isImageUpload
    fun setImageUploadState(state: Boolean){
        _isImageUpload.value = state
    }

    fun insertComment(comment: Comment, imageUrl: String?){
        viewModelScope.launch(Dispatchers.IO){
            try {
                Log.d(TAG, "insertComment: $imageUrl")
                if(imageUrl != null) {
                    val file = File(imageUrl)
                    var fileName = "comments/" + System.currentTimeMillis().toString() + ".png"
                    comment.img = fileName
                    var requestBody: RequestBody =
                        RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    var imageBody: MultipartBody.Part =
                        MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
                    val json = Gson().toJson(comment)
                    val commentBody = RequestBody.create(
                        "application/json; charset=utf-8".toMediaTypeOrNull(),
                        json
                    )
                    val response = Retrofit.commentService.insertCommentImage(imageBody, commentBody)
                    if(response.isSuccessful && response.body() != null){
                        _commentList.postValue(response.body() as MutableList<Comment>)
                    }
                    else{
                        Log.d(TAG, "getComments: ${response}")
                    }
                }else{
                    val response = Retrofit.commentService.insertComment(comment)
                    if(response.isSuccessful && response.body() != null){
                        _commentList.postValue(response.body() as MutableList<Comment>)
                    }
                }
            }
            catch (e: Exception){
                Log.d(TAG, "getComments: ${e.message}")
            }
        }
    }

    fun updateComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.commentService.updateComment(comment)
                if(response.isSuccessful && response.body() != null){
                    _commentList.postValue(response.body() as MutableList<Comment>)
                }
                else{
                    Log.d(TAG, "getComments: ${response}")
                }
            }
            catch (e: Exception){
                Log.d(TAG, "getComments: ${e.message}")
            }
        }
    }

    fun updateCommentImage(comment: Comment, imageUrl: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val file = File(imageUrl)
                var fileName = "products/" + System.currentTimeMillis().toString() + ".png"
                var requestBody: RequestBody =
                    RequestBody.create("image/*".toMediaTypeOrNull(), file)
                var imageBody: MultipartBody.Part =
                    MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
                val json = Gson().toJson(comment)
                val commentBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),json)
                val response = Retrofit.commentService.updateCommentImage(imageBody, commentBody)
                if(response.isSuccessful && response.body() != null){
                    _commentList.postValue(response.body() as MutableList<Comment>)
                }
                else{
                    Log.d(TAG, "getComments: ${response}")
                }
            }
            catch (e: Exception){
                Log.d(TAG, "getComments: ${e.message}")
            }
        }
    }


    fun deleteComment(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Retrofit.commentService.deleteComment(id)
                if(response.isSuccessful && response.body() != null){
                    _commentList.postValue(response.body() as MutableList<Comment>)
                }
                else{
                    Log.d(TAG, "getComments: ${response}")
                }
            }
            catch (e: Exception){
                Log.d(TAG, "getComments: ${e.message}")
            }
        }
    }
}