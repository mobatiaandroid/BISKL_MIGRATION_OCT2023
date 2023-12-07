package com.example.bskl_kotlin.activity.settings.model

import com.google.gson.annotations.SerializedName

class UserprofileStudentApiModel (
    @SerializedName("user_ids")var user_ids:String,
    @SerializedName("stud_id")var stud_id:String
)