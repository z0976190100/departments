package com.z0976190100.departments.persistense.dao;

import java.sql.ResultSet;

public interface Dao<T> {

    public void saveEntity(String query);

    public void deleteEntity(String query);

    public void updateEntity(T entity);

    public ResultSet getEntityById(String query);

    public ResultSet getAllEntitiesWhere(String query);

    public ResultSet getEntitiesList(String query);


}
