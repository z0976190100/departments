package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.SQLAppRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao implements GeneralConstants {

    protected Connection getConnection() {

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName(DB_DRIVER_NAME);
            Connection c = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);

            if (c == null) throw new NullPointerException(DB_CONNECTION_FAILURE_MESSAGE);

            return c;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }
}
