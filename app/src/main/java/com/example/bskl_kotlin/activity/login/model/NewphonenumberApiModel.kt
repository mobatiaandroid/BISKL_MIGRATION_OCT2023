package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class NewphonenumberApiModel  (
    @SerializedName("email")var email:String,
    @SerializedName("code")var code:String,
    @SerializedName("mobile_no")var mobile_no:String,
    @SerializedName("users_id")var users_id:String
)
