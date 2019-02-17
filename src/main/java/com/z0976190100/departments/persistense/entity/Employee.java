package com.z0976190100.departments.persistense.entity;

public class Employee {

    private final int id;
    private final String email;

    public Employee(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
