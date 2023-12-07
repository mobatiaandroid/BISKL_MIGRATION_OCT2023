package com.example.bskl_kotlin.fragment.reports.model

import com.google.gson.annotations.SerializedName

class ReportsDataModel (

    @SerializedName("id")var id: String,
    @SerializedName("reporting_cycle")var reporting_cycle: String,
    @SerializedName("academic_year")var academic_year: String,
    @SerializedName("class_id")var class_id: String,
    @SerializedName("email_sent_date")var email_sent_date: String,
    @SerializedName("updated_on")var updated_on: String,
    @SerializedName("reportcycleid")var reportcycleid: String,
@SerializedName("file")var file: String,
@SerializedName("school_code_id")var school_code_id: String,
@SerializedName("stud_id")var stud_id: String,
@SerializedName("isams_id")var isams_id: String



/*@SerializedName("created_on")var created_on: String,*/


)