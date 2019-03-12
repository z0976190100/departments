package com.z0976190100.departments.persistense.entity;

import java.sql.Date;

public class Employee {

    private int id;
    private String name;
    private Date birthDate;
    private String email;
    private int age;
    private int departmentID;

    public Employee(int id, String email, int departmentID) {
        this.id = id;
        this.email = email;
        this.departmentID = departmentID;
    }

    public Employee(int id, String name, Date birthDate, String email, int age, int departmentID) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.age = age;
        this.departmentID = departmentID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return getEmail().equals(employee.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", departmentID=" + departmentID +
                '}';
    }
}
