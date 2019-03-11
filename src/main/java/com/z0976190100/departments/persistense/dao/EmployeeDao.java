package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.persistense.entity.Employee;

import java.util.List;

public interface EmployeeDao extends GenericDao<Employee> {

    public Employee saveEntity(Employee entity);

    public List<Employee> getAllWhere(int param);

    public List<Employee> getAllWhere(int param, int offset, int limit);

}
