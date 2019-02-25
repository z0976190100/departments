package com.z0976190100.departments.service;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.EmployeeDaoImpl;
import com.z0976190100.departments.persistense.entity.Employee;

import java.util.List;

public class EmployeeService implements GeneralConstants {

    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    public Employee saveEmployee(Employee employee) throws NotUniqueEntityException{

        // TODO: check if email is unique
        if(employeeDao.getAllEntitiesWhere(employee.getEmail()).size() !=0 )
            throw new NotUniqueEntityException(EMPLOYEE_EMAIL_NOT_UNIQUE_MESSAGE);
        return employeeDao.saveEntity(employee);
    }

    public Employee getEmployee(int id) throws ResourceNotFoundException{

        Employee employee = employeeDao.getEntityById(id);

        if ( employee == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);

        return employee;
    }

    public List<Employee> getAllEmployees(int departmentID){

        return employeeDao.getEntitiesList(departmentID);
    }

    public List<Employee> getAllEmployees(int departmentID, int offset, int limit){

        return employeeDao.getEntitiesList(departmentID, offset, limit);
    }

    public void deleteEmployee(int id) throws ResourceNotFoundException {

        Employee employee = new EmployeeDaoImpl().getEntityById(id);

        if (employee == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);

        employeeDao.deleteEntity(id);
    }

    public int getRowCount(int departmentID){
        return employeeDao.getRowCount(departmentID);
    }
}
