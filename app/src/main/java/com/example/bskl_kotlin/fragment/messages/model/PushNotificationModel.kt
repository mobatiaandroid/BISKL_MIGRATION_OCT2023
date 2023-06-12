package com.example.bskl_kotlin.fragment.messages.model

import com.example.bskl_kotlin.fragment.contactus.model.ContactUsListModel
import com.google.gson.annotations.SerializedName
import java.util.Date

class PushNotificationModel (
    @SerializedName("id")var id:Int,
    @SerializedName("pushid")var pushid:String,
    @SerializedName("message")var message:String,
    @SerializedName("url")var url:String,
    @SerializedName("favourite")var favourite:String,
    @SerializedName("isChecked")var isChecked:Boolean,
    @SerializedName("isMarked")var isMarked:Boolean,
    @SerializedName("studentArray")var studentArray:ArrayList<String>,
    @SerializedName("status")var status:String,
    @SerializedName("pushtime")var pushtime:String,
    @SerializedName("date")var date:String,
    @SerializedName("push_from")var push_from:String,
    @SerializedName("type")var type:String,
    @SerializedName("student_name")var student_name:String,
    @SerializedName("dayOfTheWeek")var dayOfTheWeek:String,
    @SerializedName("day")var day:String,
    @SerializedName("monthString")var monthString:String,
    @SerializedName("monthNumber")var monthNumber:String,
    @SerializedName("year")var year:String,
    @SerializedName("title")var title:String,
    @SerializedName("user_id")var user_id:String,
    @SerializedName("dateTime")var dateTime: Date

)