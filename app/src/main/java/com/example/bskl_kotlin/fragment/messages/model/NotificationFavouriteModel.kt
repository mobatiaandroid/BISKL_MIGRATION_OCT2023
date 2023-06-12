package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationFavouriteModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: NotificationFavouriteResponseModel
)