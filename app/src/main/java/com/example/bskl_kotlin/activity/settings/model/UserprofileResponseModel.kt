package com.example.bskl_kotlin.activity.settings.model

import com.example.bskl_kotlin.fragment.settings.model.TriggerUserResponseModel
import com.google.gson.annotations.SerializedName

class UserprofileResponseModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: UserProfileResponseResponseModel

)
class UserProfileResponseResponseModel(
    @SerializedName("response")var response: String,
    @SerializedName("statuscode")var statuscode: String,
    @SerializedName("profile")var profile: UserProfileModel,
    @SerializedName("studentlist")var studentlist: ArrayList<StudentUserModel>
)