package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class ResponseArrayModel  (
    @SerializedName("userid")var userid:String,
    @SerializedName("name")var name:String,
    @SerializedName("mobileno")var mobileno:String
)