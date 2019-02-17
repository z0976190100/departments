package com.z0976190100.departments.service;

import com.z0976190100.departments.persistense.dao.EmployeeDaoImpl;
import com.z0976190100.departments.persistense.entity.Employee;

public class EmployeeService {

    public Employee saveEmployee(String email){
        return new EmployeeDaoImpl().saveEntity(email);
    }
}
