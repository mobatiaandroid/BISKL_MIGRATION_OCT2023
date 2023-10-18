package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.google.gson.annotations.SerializedName

class ContactTypeModel (
    @SerializedName("type")var type:String?=null,
    @SerializedName("mGlobalSirnameArray")var mGlobalSirnameArray:ArrayList<GlobalListSirname>?=null
)