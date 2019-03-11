package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.exceptions.RequestParameterValidationException;

import java.util.List;

public interface DepartmentDao<T> {

    public T saveEntity(T entity) throws RequestParameterValidationException;

    public void updateEntity(T entity);

    public void deleteEntity(int id);

    public T getEntityById(int id);

    public List<T> getAll();

    public List<T> getAll(int offset, int limit);

    public List<T> getAllWhere(String param);
}
