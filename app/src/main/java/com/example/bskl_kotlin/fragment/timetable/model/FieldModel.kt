package com.example.bskl_kotlin.fragment.timetable.model

class FieldModel (
    var sortname: String? = null,
    var starttime: String? = null,
    var endtime: String? = null,
    var periodId: String="",
    var countBreak: Int = 0,
    var fridyaStartTime: String? = null,
    var fridayEndTime: String? = null,
    var fridayBreakCount: Int = 0
        )