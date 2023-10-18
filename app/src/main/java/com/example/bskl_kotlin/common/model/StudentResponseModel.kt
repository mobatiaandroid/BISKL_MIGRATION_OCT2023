package com.example.bskl_kotlin.common.model

import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.google.gson.annotations.SerializedName

class StudentResponseModel (

    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:ArrayList<StudentListModel>
)