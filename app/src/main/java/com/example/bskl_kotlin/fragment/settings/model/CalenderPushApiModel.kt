package com.example.bskl_kotlin.fragment.settings.model

import com.google.gson.annotations.SerializedName

class CalenderPushApiModel (

    @SerializedName("user_ids")var user_ids: String,
    @SerializedName("status")var status: String

)