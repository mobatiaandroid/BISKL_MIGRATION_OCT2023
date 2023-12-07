package com.example.bskl_kotlin.fragment.attendance.model;

import java.io.Serializable;

public class AttendanceModel implements Serializable {


    String id;
    String date;
    String present;
    String late;
    String authorised_absence;
    String unauthorised_absence;
    String tagList;

    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getAuthorised_absence() {
        return authorised_absence;
    }

    public void setAuthorised_absence(String authorised_absence) {
        this.authorised_absence = authorised_absence;
    }

    public String getUnauthorised_absence() {
        return unauthorised_absence;
    }

    public void setUnauthorised_absence(String unauthorised_absence) {
        this.unauthorised_absence = unauthorised_absence;
    }
}
