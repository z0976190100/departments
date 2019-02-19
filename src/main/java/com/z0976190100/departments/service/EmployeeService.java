package com.z0976190100.departments.service;

import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.EmployeeDaoImpl;
import com.z0976190100.departments.persistense.entity.Employee;

import java.util.List;

public class EmployeeService {

    public Employee saveEmployee(String email, int departmentID){
        return new EmployeeDaoImpl().saveEntity(email, departmentID);
    }

    public List<Employee> getAllEmployees(int departmentID){

        return new EmployeeDaoImpl().getEntitiesList(departmentID);
    }

    public List<Employee> getAllEmployees(int departmentID, int offset, int limit){

        return new EmployeeDaoImpl().getEntitiesList(departmentID, offset, limit);
    }

    public void deleteEmployee(int id) throws ResourceNotFoundException {
        new EmployeeDaoImpl().deleteEntity(id);
    }

    public int getRowCount(int departmentID){
        return new EmployeeDaoImpl().getRowCount(departmentID);
    }
}
