package com.z0976190100.departments.persistense.entity;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private int id;
    private String title;
    private List<Employee> employeeList = new ArrayList<>();


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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
