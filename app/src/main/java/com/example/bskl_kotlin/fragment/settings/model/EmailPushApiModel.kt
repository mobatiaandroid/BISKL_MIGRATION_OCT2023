package com.example.bskl_kotlin.fragment.settings.model

import com.google.gson.annotations.SerializedName

class EmailPushApiModel (

    @SerializedName("user_ids")var user_ids: String,
    @SerializedName("status")var status: String

)