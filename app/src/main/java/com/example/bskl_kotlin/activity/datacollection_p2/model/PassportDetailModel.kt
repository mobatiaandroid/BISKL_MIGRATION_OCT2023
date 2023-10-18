package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.google.gson.annotations.SerializedName

class PassportDetailModel  (
    @SerializedName("id")var id:String?=null,
    @SerializedName("student_id")var student_id:String?=null,
    @SerializedName("student_name")var student_name:String?=null,
    @SerializedName("not_have_a_valid_passport")var not_have_a_valid_passport:String?=null,
    @SerializedName("passport_number")var passport_number:String?=null,
    @SerializedName("nationality")var nationality:String?=null,
    @SerializedName("passport_image")var passport_image:String?=null,
    @SerializedName("date_of_issue")var date_of_issue:String?=null,
    @SerializedName("expiry_date")var expiry_date:String?=null,
    @SerializedName("passport_expired")var passport_expired:String?=null,
    @SerializedName("visa")var visa:String?=null,
    @SerializedName("visa_permit_no")var visa_permit_no:String?=null,
    @SerializedName("visa_permit_expiry_date")var visa_permit_expiry_date:String?=null,
    @SerializedName("visa_expired")var visa_expired:String?=null,
    @SerializedName("visa_image")var visa_image:String?=null,
    @SerializedName("photo_no_consent")var photo_no_consent:String?=null,
    @SerializedName("status")var status:String?=null,
    @SerializedName("request")var request:String?=null,
    @SerializedName("created_at")var created_at:String?=null,
    @SerializedName("updated_at")var updated_at:String?=null,
    @SerializedName("isPassportDateChanged")var isPassportDateChanged:Boolean=false,
    @SerializedName("isVisaDateChanged")var isVisaDateChanged:Boolean=false

    )