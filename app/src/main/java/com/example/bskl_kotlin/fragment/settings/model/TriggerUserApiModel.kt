package com.example.bskl_kotlin.fragment.settings.model

import com.google.gson.annotations.SerializedName

class TriggerUserApiModel (

    @SerializedName("user_ids")var user_ids: String,
    @SerializedName("trigger_type")var trigger_type: String

)