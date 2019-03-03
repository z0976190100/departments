package com.z0976190100.departments.persistense.dao;

import java.util.List;

public interface DaoAlt<T> {

    public T saveEntity(T entity);

    public void deleteEntity(int id);

    public int updateEntity(T entity);

    public T getEntityById(int id);

    public List<T> getAllWhere(String param);

    public List<T> getAllWhere(int param);

    public List<T> getAllWhere(int param, int offset, int limit);

}
