package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class TimeTableStudentModel (
    @SerializedName("student_name")var student_name:String,
    @SerializedName("id")var id:String,
    @SerializedName("type")var type:String
)