package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class UserDetailsModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response:UserResponseModel
)