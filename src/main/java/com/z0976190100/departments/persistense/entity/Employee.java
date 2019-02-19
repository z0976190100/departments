package com.z0976190100.departments.persistense.entity;

public class Employee {

    private final int id;
    private final String email;
    private final int departmentID;

    public Employee(int id, String email, int departmentID) {
        this.id = id;
        this.email = email;
        this.departmentID = departmentID;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
