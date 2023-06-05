package com.example.bskl_kotlin.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class ContactUsListModel (
    @SerializedName("name")var name:String,
    @SerializedName("phone")var phone:String,
    @SerializedName("email")var email:String
)