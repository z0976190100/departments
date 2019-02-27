package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.persistense.entity.Employee;

import java.sql.SQLException;
import java.util.List;

interface DaoAlt<T> {

    public T saveEntity(T entity);

    public void deleteEntity(int id);

    public int updateEntity(T entity);

    public Employee getEntityById(int id);

    public List<T> getAllEntitiesWhere(String title);

    public List<T> getEntitiesList(int departmentID);

    public List<T> getEntitiesList(int departmentID, int offset, int limit);

    public int getRowCount(int departmentID) throws SQLException;

}
