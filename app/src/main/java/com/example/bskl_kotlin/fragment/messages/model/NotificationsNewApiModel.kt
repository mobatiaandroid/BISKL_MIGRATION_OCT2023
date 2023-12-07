package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationsNewApiModel  (

    @SerializedName("deviceid")var deviceid: String,
    @SerializedName("devicetype")var devicetype:String,
    @SerializedName("users_id")var users_id:String,
    @SerializedName("type")var type:String,
    @SerializedName("limit")var limit:String,
    @SerializedName("page")var page:String

)