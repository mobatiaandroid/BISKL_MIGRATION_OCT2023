package com.example.bskl_kotlin.fragment.attendance.model;

import java.io.Serializable;

public class AttendanceChartListModel implements Serializable {


    String name;
    String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
