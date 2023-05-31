package com.example.bskl_kotlin.activity.splash

import com.google.gson.annotations.SerializedName

class TokenModel  (
    @SerializedName("grant_type")var grant_type:String,
    @SerializedName("client_id")var client_id:String,
    @SerializedName("client_secret")var client_secret:String,
    @SerializedName("username")var username:String,
    @SerializedName("password")var password:String

)