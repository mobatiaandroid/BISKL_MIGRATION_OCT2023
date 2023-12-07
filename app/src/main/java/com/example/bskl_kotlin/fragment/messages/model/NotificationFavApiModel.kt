package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationFavApiModel (

    @SerializedName("userid")var userid: String,
    @SerializedName("favourite")var favourite:String,
    @SerializedName("pushid")var pushid:String

)