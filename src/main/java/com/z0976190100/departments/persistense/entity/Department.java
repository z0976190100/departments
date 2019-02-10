package com.z0976190100.departments.persistense.entity;

public class Department {

    private int id;
    private String title;


    public Department(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        return;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        return;
    }
}
