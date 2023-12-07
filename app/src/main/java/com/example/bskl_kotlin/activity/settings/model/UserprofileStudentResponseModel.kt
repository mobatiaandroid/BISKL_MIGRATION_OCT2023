package com.example.bskl_kotlin.activity.settings.model

import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.kingsapp.activities.calender.model.StudentDetailModel
import com.google.gson.annotations.SerializedName

class UserprofileStudentResponseModel  (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: UserProfileStudentDetailResponseModel

)
class UserProfileStudentDetailResponseModel(
    @SerializedName("response")var response: String,
    @SerializedName("statuscode")var statuscode: String,
    @SerializedName("stud_photo")var stud_photo: String,
    @SerializedName("studentdetails")var studentdetails: ArrayList<StudentDetailModelNew>,
    @SerializedName("passport")var passport: ArrayList<PassportDetailModelNew>,
    @SerializedName("visa_permit")var visa_permit: ArrayList<PassportDetailModelNew>,
    @SerializedName("medical_insurance")var medical_insurance: ArrayList<PassportDetailModelNew>,
    @SerializedName("personal_accident_insurance")var personal_accident_insurance: ArrayList<PassportDetailModelNew>,
    @SerializedName("contact_details")var contact_details: ContactDetails
)
class ContactDetails(
    @SerializedName("other_contact")var other_contact: ArrayList<OwnDetailsModel>,
    @SerializedName("next_of_kin_contact")var next_of_kin_contact: ArrayList<KinDetailsModel>,
    @SerializedName("local_emergency_contact")var local_emergency_contact: ArrayList<LocalEmergencyModel>
)