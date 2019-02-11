package com.z0976190100.departments.persistense.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Dao<T> {

    public void saveEntity(String query);

    public void deleteEntity(String query);

    public void updateEntity(T entity);

    public ResultSet getEntityById(String query);

    public List<Map<String, Object>> getAllEntitiesWhere(String query);

    public ResultSet getEntitiesList(String query);


}
