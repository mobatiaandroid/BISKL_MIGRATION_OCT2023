package com.example.bskl_kotlin.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class ContactUsDataModel(
    @SerializedName("description")var description:String,
    @SerializedName("latitude")var latitude:String,
    @SerializedName("longitude")var longitude:String,
    @SerializedName("contacts")var contacts:ArrayList<ContactUsListModel>
)