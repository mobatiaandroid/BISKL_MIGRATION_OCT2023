package com.example.bskl_kotlin.activity.absence.model

import com.google.gson.annotations.SerializedName

class RequestLeaveApiModel (
    @SerializedName("student_id")var student_id:String,
    @SerializedName("users_id")var users_id:String,
    @SerializedName("from_date")var from_date:String,
    @SerializedName("to_date")var to_date:String,
    @SerializedName("reason")var reason:String
)