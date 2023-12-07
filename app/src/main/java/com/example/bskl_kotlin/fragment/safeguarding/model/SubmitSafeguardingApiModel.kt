package com.example.bskl_kotlin.fragment.safeguarding.model

import com.google.gson.annotations.SerializedName

class SubmitSafeguardingApiModel (
    @SerializedName("user_ids")var user_ids :String,
    @SerializedName("student_id")var student_id :String,
    @SerializedName("attendance_id")var attendance_id :String,
    @SerializedName("status")var status :String,
    @SerializedName("details")var details :String
)