package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.google.gson.annotations.SerializedName

class KinModel (
    @SerializedName("id")var id:String,
    @SerializedName("user_id")var user_id:String,
    @SerializedName("kin_id")var kin_id:String,
    @SerializedName("title")var title:String,
    @SerializedName("name")var name:String,
    @SerializedName("last_name")var last_name:String,
    @SerializedName("relationship")var relationship:String,
    @SerializedName("email")var email:String,
    @SerializedName("phone")var phone:String,
    @SerializedName("code")var code:String,
    @SerializedName("user_mobile")var user_mobile:String,
    @SerializedName("student_id")var student_id:String,
    @SerializedName("correspondencemailmerge")var correspondencemailmerge:String,
    @SerializedName("reportmailmerge")var reportmailmerge:String,
    @SerializedName("justcontact")var justcontact:String,
    @SerializedName("status")var status:String,
    @SerializedName("request")var request:String,
    @SerializedName("device_meta")var device_meta:String,
    @SerializedName("created_at")var created_at:String,
    @SerializedName("updated_at")var updated_at:String,
    @SerializedName("IsClicked")var IsClicked:Boolean,
    @SerializedName("isFullFilled")var isFullFilled:Boolean,
    @SerializedName("NewData")var NewData:String,
    @SerializedName("isNewData")var isNewData:Boolean,
    @SerializedName("isConfirmed")var isConfirmed:Boolean,


    )