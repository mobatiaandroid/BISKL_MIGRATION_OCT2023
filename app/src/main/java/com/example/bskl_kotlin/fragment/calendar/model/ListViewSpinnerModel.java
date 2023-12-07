package com.example.bskl_kotlin.fragment.calendar.model;

import java.io.Serializable;

public class ListViewSpinnerModel implements Serializable {
    String filename;
    String title;
    String id;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}