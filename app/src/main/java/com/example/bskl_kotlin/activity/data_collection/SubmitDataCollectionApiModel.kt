package com.example.bskl_kotlin.activity.data_collection

import com.example.bskl_kotlin.activity.settings.model.ContactDetails
import com.example.bskl_kotlin.activity.settings.model.PassportDetailModelNew
import com.example.bskl_kotlin.activity.settings.model.StudentDetailModelNew
import com.google.gson.annotations.SerializedName

class SubmitDataCollectionApiModel(
    @SerializedName("user_ids")var user_ids:String,
    @SerializedName("overall_status")var overall_status:String,
    @SerializedName("data")var data:String,
    @SerializedName("trigger_type")var trigger_type:String,
    @SerializedName("device_type")var device_type:String,
    @SerializedName("device_name")var device_name:String,
    @SerializedName("app_version")var app_version:String


)
