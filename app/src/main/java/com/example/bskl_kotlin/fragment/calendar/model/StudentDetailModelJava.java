package com.example.bskl_kotlin.fragment.calendar.model;

import java.io.Serializable;

public class StudentDetailModelJava implements Serializable {

    private String student_id;

    private String name;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
