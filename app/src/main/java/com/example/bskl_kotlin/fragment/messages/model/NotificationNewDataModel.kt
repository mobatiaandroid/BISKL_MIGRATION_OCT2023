package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationNewDataModel (
    @SerializedName("pushid")var pushid:String,
    @SerializedName("title")var title:String,
    @SerializedName("message")var message:String,
    @SerializedName("htmlmessage")var htmlmessage:String,
    @SerializedName("date")var date:String,
    @SerializedName("push_from")var push_from:String,
    @SerializedName("type")var type:String,
    @SerializedName("favourite")var favourite:String,
    @SerializedName("url")var url:String,
    @SerializedName("student_name")var student_name:String


)