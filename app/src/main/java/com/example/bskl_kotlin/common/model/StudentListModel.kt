package com.example.bskl_kotlin.common.model

import com.google.gson.annotations.SerializedName

class StudentListModel (
    @SerializedName("id")var id:String,
    @SerializedName("name")var name:String,
    @SerializedName("class")var mClass:String,
    @SerializedName("section")var section:String,
    @SerializedName("house")var house:String,
    @SerializedName("photo")var photo:String,
    @SerializedName("progressreport")var progressreport:String,
    @SerializedName("alumi")var alumi:String,
    @SerializedName("type")var type:String,
)