package com.example.bskl_kotlin.fragment.absence.model

import com.google.gson.annotations.SerializedName

class LeaveRequestsApiModel(

    @SerializedName("users_id")var users_id: String,
    @SerializedName("student_id")var student_id: String

)