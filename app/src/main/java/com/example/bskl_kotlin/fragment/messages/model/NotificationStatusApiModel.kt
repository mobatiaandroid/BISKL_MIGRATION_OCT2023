package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationStatusApiModel (

    @SerializedName("userid")var userid: String,
    @SerializedName("status")var status:String,
    @SerializedName("pushid")var pushid:String

)