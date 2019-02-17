package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.AppRuntimeException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.persistense.entity.Employee;

import java.sql.*;
import java.util.List;

public class EmployeeDaoImpl implements DaoAlt<Employee>, GeneralConstants {

    private Connection getNullsafeConnection() {

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

    @Override
    public Employee saveEntity(String email) {

        Employee newEmployee = null;

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO employee2 (login , id) VALUES ( ? , DEFAULT) RETURNING *;")) {

                ps.setString(1, email);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("saved");
                    resultSet.next();
                    newEmployee = new Employee(resultSet.getInt(ID), resultSet.getString("login"));
                } catch (SQLException e) {
                    e.printStackTrace();

                    for (Throwable t : e) {
                        System.out.println(t.getMessage());
                    }
                    //TODO init fails
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // TODO prepared fails
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
        return newEmployee;
    }

    @Override
    public void deleteEntity(int id) throws ResourceNotFoundException {

    }

    @Override
    public void updateEntity(Employee entity) throws ResourceNotFoundException {

    }

    @Override
    public Department getEntityById(int id) {
        return null;
    }

    @Override
    public List<Employee> getAllEntitiesWhere(String title) {
        return null;
    }

    @Override
    public List<Employee> getEntitiesList() {
        return null;
    }

    @Override
    public List<Employee> getEntitiesList(int offset, int limit) {
        return null;
    }

    @Override
    public int getRowCount(String query) throws SQLException {
        return 0;
    }
}
