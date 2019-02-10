package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.exceptions.AppRuntimeException;
import com.z0976190100.departments.app_constants.General;
import com.z0976190100.departments.app_constants.Messages;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DaoImpl<T> implements Dao<T>, General, Messages {

    private Connection getConnection() {

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName(DB_DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new AppRuntimeException("Connection to DB is failed");
        }
    }

    @Override
    public ResultSet saveEntity(String query) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteEntity(String query) {

    }

    @Override
    public void updateEntity(T entity) {

    }

    @Override
    public ResultSet getEntityById(String query) {

        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getAllEntitiesWhere(String query) {
        return null;
    }
}