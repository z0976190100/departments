package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.persistense.entity.Department;

import java.util.List;

public interface DepartmentDao extends GenericDao<Department> {

    public Department saveEntity(Department entity) throws RequestParameterValidationException;

    public List<Department> getAll();

    public List<Department> getAll(int offset, int limit);

}
