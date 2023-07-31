package com.example.bskl_kotlin.common.model

import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.ResponseArrayUserModel
import com.example.bskl_kotlin.activity.home.model.SocialMediaUserModel
import com.example.bskl_kotlin.activity.home.model.TimeTableStudentModel
import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.google.gson.annotations.SerializedName

class StudentResponseModel (

    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:ArrayList<StudentModel>
        )