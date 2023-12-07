package com.example.bskl_kotlin.activity.calender.utils;

import android.widget.TextView;

import java.util.Date;

public interface CalendarListener {
     void onDateSelected(Date date);

    void onMonthChanged(Date time, TextView btnWeekView, TextView btnMonthView, TextView btnYearView);
    void onWeekChanged(Date time, TextView btnWeekView, TextView btnMonthView, TextView btnYearView);
    void onWeekView(Date time, TextView btnWeekView, TextView btnMonthView, TextView btnYearView);
    void onMonthView(Date time, TextView btnWeekView, TextView btnMonthView, TextView btnYearView);
    void onYearView(Date time, TextView btnWeekView, TextView btnMonthView, TextView btnYearView);
}
