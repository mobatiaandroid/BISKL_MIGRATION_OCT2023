package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class SocialMediaUserModel (
    @SerializedName("id")var id:String,
    @SerializedName("fbkey")var fbkey:String,
    @SerializedName("inkey")var inkey:String,
    @SerializedName("youtubekey")var youtubekey:String

)