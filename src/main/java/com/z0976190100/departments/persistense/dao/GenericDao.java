package com.z0976190100.departments.persistense.dao;

import java.util.List;

public interface GenericDao<T> {

    public int updateEntity(T entity);

    public void deleteEntity(int id);

    public T getEntityById(int id);

    public List<T> getAllWhere(String param);

}
