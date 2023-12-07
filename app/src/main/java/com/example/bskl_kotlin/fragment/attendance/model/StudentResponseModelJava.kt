package com.example.bskl_kotlin.fragment.attendance.model

import com.example.bskl_kotlin.fragment.safeguarding.model.AttendancedataList
import com.example.bskl_kotlin.fragment.safeguarding.model.CodesModel
import com.google.gson.annotations.SerializedName

class StudentResponseModelJava (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: StudentResponseModelJavaResponse
)

class StudentResponseModelJavaResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:ArrayList<StudentModelJava>
)