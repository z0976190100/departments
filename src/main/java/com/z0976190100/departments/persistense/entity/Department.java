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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode();
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
