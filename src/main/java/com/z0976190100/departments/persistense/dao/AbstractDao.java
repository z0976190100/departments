package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.AppRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao implements GeneralConstants {

    protected Connection getNullsafeConnection() {

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName(DB_DRIVER_NAME);
            Connection c = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);

            if (c == null) throw new NullPointerException(DB_CONNECTION_FAILURE_MESSAGE);

            return c;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // FIXME do checked exception for DB_CONNECTION_FAILURE
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }
}
