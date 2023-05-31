package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class ResponseArrayUserModel (
    @SerializedName("userid")var userid:String,
    @SerializedName("name")var name:String,
    @SerializedName("mobileno")var mobileno:String,
    @SerializedName("calenderpush")var calenderpush:String,
    @SerializedName("messagebadge")var messagebadge:String,
    @SerializedName("emailpush")var emailpush:String,
    @SerializedName("calenderbadge")var calenderbadge:String,
    @SerializedName("reportmailmerge")var reportmailmerge:String,
    @SerializedName("correspondencemailmerge")var correspondencemailmerge:String,
    @SerializedName("studentdetails")var studentdetails:ArrayList<StudDetailsUsermodel>,
    @SerializedName("safeguarding_notification")var safeguarding_notification:String,
    @SerializedName("data_collection")var data_collection:String,
    @SerializedName("trigger_type")var trigger_type:String,
    @SerializedName("trigger_date")var trigger_date:String,
    @SerializedName("already_triggered")var already_triggered:String
)