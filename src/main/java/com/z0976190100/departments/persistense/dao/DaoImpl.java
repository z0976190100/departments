package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.General;
import com.z0976190100.departments.app_constants.Messages;
import com.z0976190100.departments.exceptions.AppRuntimeException;

import java.sql.*;

public class DaoImpl<T> implements Dao<T>, General, Messages {

    private Connection getConnection() {

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName(DB_DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }

    private ResultSet getResultSet(String query) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            return rs;

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }

    private int doExecuteUpdate(String query) {
        try (Connection connection = getConnection()) {

            int i = connection.prepareStatement(query).executeUpdate();
            return i;

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }

    @Override
    public ResultSet getEntitiesList(String query) {
        return getResultSet(query);
    }

    @Override
    public void saveEntity(String query) {
        doExecuteUpdate(query);
    }

    @Override
    public void deleteEntity(String query) {
        doExecuteUpdate(query);
    }


    @Override
    public void updateEntity(T entity) {

    }

    @Override
    public ResultSet getEntityById(String query) {

        return getResultSet(query);
    }

    @Override
    public ResultSet getAllEntitiesWhere(String query) {

        return getResultSet(query);
    }

    @Override
    public int getRowCount(String query) throws SQLException {

        ResultSet resultSet = getResultSet(query);
        resultSet.next();
        int rowCount = resultSet.getInt(1);
        resultSet.close();
        return rowCount;
    }
}
