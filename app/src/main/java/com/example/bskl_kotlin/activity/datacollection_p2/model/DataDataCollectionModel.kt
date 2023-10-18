package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.google.gson.annotations.SerializedName

class DataDataCollectionModel (
    @SerializedName("display_message")var display_message:String,
    @SerializedName("own_details")var own_details:ArrayList<OwnContactModel>,
    @SerializedName("kin_details")var kin_details:ArrayList<KinModel>,
    @SerializedName("GlobalList")var GlobalList:ArrayList<GlobalListDataModel>,
    @SerializedName("health_and_insurence")var health_and_insurence:ArrayList<InsuranceDetailModel>,
    @SerializedName("passport_details")var passport_details:ArrayList<PassportDetailModel>,
)