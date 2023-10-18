package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class StudentDetailSettingModel  (
    @SerializedName("id")var id:String,
    @SerializedName("alumi")var alumi:String,
    @SerializedName("applicant")var applicant:String
)