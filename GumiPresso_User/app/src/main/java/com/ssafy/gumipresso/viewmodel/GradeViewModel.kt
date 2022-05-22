package com.ssafy.gumipresso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.gumipresso.model.dto.Grade

private const val TAG = "GradeViewModel"
class GradeViewModel: ViewModel() {

    private val _grade = MutableLiveData<Grade>()
    var grade: LiveData<Grade>? = null
        get() = _grade

    private fun getGrade(stamps: Int){
        val grade = Grade(0, "씨앗", 1,"seeds.png", stamps, 0)
        var stamp = stamps
        if(stamps < 50){
            grade.id = 1
            grade.title = "씨앗"
            grade.grade = stamps / 10 + 1
            grade.remain = grade.grade * 10 - stamps
        }
        else if(stamps < 125){
            stamp -= 50
            grade.id = 2
            grade.title = "꽃"
            grade.img = "flower.png"
            grade.grade = stamp / 15 + 1
            grade.remain = grade.grade * 15 - stamp
        }
        else if(stamps < 225){
            stamp -= 125
            grade.id = 3
            grade.title = "열매"
            grade.img = "coffee_fruit.png"
            grade.grade = stamp / 20 + 1
            grade.remain = grade.grade * 20 - stamp
        }
        else if(stamps < 350){
            stamp -= 225
            grade.id = 4
            grade.title = "커피콩"
            grade.img = "coffee_beans.png"
            grade.grade = stamp / 25 + 1
            grade.remain = grade.grade * 25 - stamp
        }
        else{
            grade.id = 5
            grade.title = "나무"
            grade.img = "coffee_tree.png"
            grade.grade = 5
        }
        _grade.postValue(grade)
    }

    fun getUserGrade(stamps: Int): MutableLiveData<Grade> {
        getGrade(stamps)

        return _grade
    }

//    단계별 10 15 20 25
//
//    씨앗
//    0~9 1단계
//    10~19 2단계
//    20~29 3단계
//    30~39 4단계
//    40~49 5단계
//
//    꽃
//    50~64 1단계
//    65~79 2단계
//    80~94 3단계
//    95~109 4단계
//    110~124 5단계
//
//    열매
//    125~144 1단계
//    145~164 2단계
//    165~184 3단계
//    185~204 4단계
//    205~224 5단계
//
//    커피콩
//    225~249 1단계
//    250~274 2단계
//    275~299 3단계
//    300~324 4단계
//    325~349 5단계
//
//    350~ 나무

}