package com.example.bskl_kotlin.common.model

import com.example.bskl_kotlin.activity.home.model.UserResponseModel
import com.google.gson.annotations.SerializedName

class StudentListResponseModel (

    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: StudentResponseModel
        )