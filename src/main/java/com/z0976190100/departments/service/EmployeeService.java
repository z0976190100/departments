package com.z0976190100.departments.service;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.AgeNotConsistentException;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.EmployeeDao;
import com.z0976190100.departments.persistense.dao.EmployeeDaoImpl;
import com.z0976190100.departments.persistense.entity.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeService implements GeneralConstants {

    private EmployeeDao<Employee> dao = new EmployeeDaoImpl();

    public Employee saveEmployee(Employee employee) throws NotUniqueEntityException, AgeNotConsistentException{

        if(dao.getAllWhere(employee.getEmail()).size() !=0 )
            throw new NotUniqueEntityException(EMPLOYEE_EMAIL_NOT_UNIQUE_MESSAGE);

        if(!ageConsistent(employee.getAge(), employee.getBirthDate()))
            throw new AgeNotConsistentException(EMPLOYEE_AGE_NOT_VALID_MESSAGE);

        return dao.saveEntity(employee);
    }

    public Employee updateEmployee (Employee employee) throws AgeNotConsistentException, ResourceNotFoundException{

        if ( dao.getEntityById(employee.getId()) == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);

        if(!ageConsistent(employee.getAge(), employee.getBirthDate()))
            throw new AgeNotConsistentException(EMPLOYEE_AGE_NOT_VALID_MESSAGE);
        dao.updateEntity(employee);
        return   dao.getEntityById(employee.getId());

    }

    public void deleteEmployee(int id) throws ResourceNotFoundException {

        Employee employee = new EmployeeDaoImpl().getEntityById(id);

        if (employee == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);

        dao.deleteEntity(id);
    }

    public void deleteAllEmployees(int departmentID) {

        ((EmployeeDaoImpl)dao).deleteAllEntitiesWhere(departmentID);

    }

    public Employee getEmployee(int id) throws ResourceNotFoundException{

        Employee employee = dao.getEntityById(id);

        if ( employee == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);

        return employee;
    }

    public List<Employee> getAllEmployees(int departmentID){

        return dao.getAllWhere(departmentID);
    }

    public List<Employee> getAllEmployees(int departmentID, int offset, int limit){

        return dao.getAllWhere(departmentID, offset, limit);
    }

    public int getRowCount(int departmentID){
        return ((EmployeeDaoImpl)dao).getAllWhereRowCount(departmentID);
    }

    private boolean ageConsistent(int age, Date bd){

        LocalDate birthD = bd.toLocalDate();
        LocalDate today = LocalDate.now();

        if(birthD.plusYears(age).isEqual(today)) return true;

        if(birthD.getDayOfYear() > today.getDayOfYear() && today.getYear()-1 == birthD.plusYears(age).getYear()) return true;

        if(birthD.getDayOfYear() < today.getDayOfYear() && today.getYear() == birthD.plusYears(age).getYear()) return true;

        return false;
    }
}
