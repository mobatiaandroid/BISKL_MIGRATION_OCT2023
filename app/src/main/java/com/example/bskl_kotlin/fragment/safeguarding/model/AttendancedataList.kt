package com.example.bskl_kotlin.fragment.safeguarding.model

import com.google.gson.annotations.SerializedName

class AttendancedataList (
    var id: Int? = null,
    var absenceCodeId: Int? = null,
    var isPresent: String? = null,
    var date: String? = null,
    var schoolId: String? = null,
    var isLate: String? = null,
    var student_name: String? = null,
    @SerializedName("class") var mclass: String? = null,
    var form: String? = null,
    var stud_id: String? = null,
    var photo: String? = null,
    var status: String? = null,
    var parent_id: String? = null,
    var parent_name: String? = null,
    var app_updated_on: String? = null,
    var registrationComment: String? = null,
        )