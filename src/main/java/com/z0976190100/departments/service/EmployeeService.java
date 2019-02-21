package com.z0976190100.departments.service;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.EmployeeDaoImpl;
import com.z0976190100.departments.persistense.entity.Employee;

import java.util.List;

public class EmployeeService implements GeneralConstants {

    public Employee saveEmployee(String email, int departmentID) throws NotUniqueEntityException{

        // TODO: check if email is unique
        if(new EmployeeDaoImpl().getAllEntitiesWhere(email).size() !=0 )
            throw new NotUniqueEntityException(EMPLOYEE_EMAIL_NOT_UNIQUE_MESSAGE);
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
