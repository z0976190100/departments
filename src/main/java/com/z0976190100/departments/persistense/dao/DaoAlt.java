package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.persistense.entity.Employee;

import java.sql.SQLException;
import java.util.List;

interface DaoAlt<T> {

    public T saveEntity(String title);

    public void deleteEntity(int id) throws ResourceNotFoundException;

    public void updateEntity(T entity) throws ResourceNotFoundException;

    public Department getEntityById(int id);

    public List<T> getAllEntitiesWhere(String title);

    public List<T> getEntitiesList();

    public List<T> getEntitiesList(int offset, int limit);

    public int getRowCount(String query) throws SQLException;

}
