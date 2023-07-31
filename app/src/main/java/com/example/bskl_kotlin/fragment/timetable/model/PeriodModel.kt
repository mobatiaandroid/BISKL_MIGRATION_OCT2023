package com.example.bskl_kotlin.fragment.timetable.model

class PeriodModel (
    var sortname: String? = null,
    var starttime: String? = null,
    var endtime: String? = null,
    var period_id: String? = null,
    var monday: String? = null,
    var tuesday: String? = null,
    var wednesday: String? = null,
    var thursday: String? = null,
    var friday: String? = null,
    var countM: Int = 0,
    var countT: Int = 0,
    var countW: Int = 0,
    var countTh: Int = 0,
    var countF: Int = 0,
    var countBreak: Int = 0,
    var timeTableDayModel: ArrayList<DayModel?>? = null,
    var timeTableListM: ArrayList<DayModel?>? = null,
    var timeTableListT: ArrayList<DayModel?>? = null,
    var timeTableListW: ArrayList<DayModel?>? = null,
    var timeTableListTh: ArrayList<DayModel?>? = null,
    var timeTableListF: ArrayList<DayModel?>? = null
        )