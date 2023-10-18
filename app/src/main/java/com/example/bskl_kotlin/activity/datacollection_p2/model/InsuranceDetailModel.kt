package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.google.gson.annotations.SerializedName

class InsuranceDetailModel  (
    @SerializedName("id")var id:String,
    @SerializedName("student_id")var student_id:String,
    @SerializedName("student_name")var student_name:String,
    @SerializedName("health_detail")var health_detail:String,
    @SerializedName("no_personal_accident_insurance")var no_personal_accident_insurance:String,
    @SerializedName("medical_insurence_policy_no")var medical_insurence_policy_no:String,
    @SerializedName("medical_insurence_member_number")var medical_insurence_member_number:String,
    @SerializedName("medical_insurence_provider")var medical_insurence_provider:String,
    @SerializedName("medical_insurence_expiry_date")var medical_insurence_expiry_date:String,
    @SerializedName("personal_accident_insurence_policy_no")var personal_accident_insurence_policy_no:String,
    @SerializedName("personal_accident_insurence_provider")var personal_accident_insurence_provider:String,
    @SerializedName("personal_acident_insurence_expiry_date")var personal_acident_insurence_expiry_date:String,
    @SerializedName("preferred_hospital")var preferred_hospital:String,
    @SerializedName("status")var status:String,
    @SerializedName("request")var request:String,
    @SerializedName("created_at")var created_at:String,
    @SerializedName("updated_at")var updated_at:String,
    @SerializedName("declaration")var declaration:String

    )