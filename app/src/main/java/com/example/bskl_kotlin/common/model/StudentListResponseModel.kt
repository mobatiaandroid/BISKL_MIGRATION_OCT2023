package com.example.bskl_kotlin.common.model

import com.google.gson.annotations.SerializedName

class StudentListResponseModel (

    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: StudentResponseModel
)