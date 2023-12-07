package com.example.bskl_kotlin.fragment.absence.model

class StudentModel (
    val VIEW_TYPE1: Int = 1,

    val VIEW_TYPE2: Int = 2,

    val VIEW_TYPE3: Int = 3,
    var abscenceId //attendance Id
    : String? = null,
    var absenceCodeId: String? = null,
    var abRegister: String? = null,
    var isPresent: String? = null,
    var isLate: String? = null,
    var date: String? = null,
    var schoolId: String? = null,
    var status: String? = null,
    var parent_id: String? = null,
    var parent_name: String? = null,
    var app_updated_on: String? = null,
    var type: String? = null,
    var registrationComment: String? = null,

    var mId: String? = null,
    var mName: String? = null,
    var mClass: String? = null,
    var mSection: String? = null,
    var progressreport: String? = null,
    var alumini: String? = null,
    var goingStatus: String? = null,
    var mPhoto: String? = null,
    var mHouse: String? = null,
    var viewType: String? = null
        )