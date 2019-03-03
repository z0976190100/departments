package com.z0976190100.departments.persistense.dao;

import java.util.List;

public interface Dao<T> {

    public T saveEntity(T entity);

    public void deleteEntity(int id);

    public void updateEntity(T entity);

    public T getEntityById(int id);

    public List<T> getAll();

    public List<T> getAll(int offset, int limit);
}
