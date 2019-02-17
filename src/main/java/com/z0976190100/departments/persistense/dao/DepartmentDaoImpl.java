package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.AppRuntimeException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.persistense.entity.DepartmentEntityDescription;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.persistense.entity.EntityDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements Dao<Department>, GeneralConstants {


    private final EntityDescription description = new DepartmentEntityDescription();
    private final String table = description.getTableName();
    private final String unique = description.getUniqueField();

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

    private ResultSet getResultSet(String query) {

        try (Connection connection = getNullsafeConnection()) {

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            return rs;

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }

    @Override
    public List<Department> getEntitiesList() {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " ORDER BY id ;")) {

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                e.printStackTrace();
                // TODO prepared fails
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return departmentList;
    }

    @Override
    public List<Department> getEntitiesList(int offset, int limit) {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table +
                    " ORDER BY id" +
                    " LIMIT " + limit + " OFFSET " + offset + ";")) {

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                e.printStackTrace();
                // TODO prepared fails
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return departmentList;
    }

    private void getEntityListHelper(List<Department> departmentList, PreparedStatement ps) {
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                departmentList.add(new Department(rs.getInt(ID), rs.getString(TITLE)));
            }

        } catch (SQLException e) {
            e.printStackTrace();

            for (Throwable t : e) {
                System.out.println(t.getMessage());
            }
            //TODO init fails
        }
    }

    @Override
    public void saveEntity(String title) {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO " + table + " (title , id) VALUES ( ? , DEFAULT);")) {

                ps.setString(1, title);

                try {
                    ps.executeUpdate();
                    System.out.println("saved");
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

    }

    @Override
    public void deleteEntity(int id) throws ResourceNotFoundException {

        Department d = getEntityById(id);

        if (d == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSSAGE);

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?;")) {

                ps.setInt(1, id);

                try {
                    ps.executeUpdate();
                    System.out.println("deleted");
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
    }


    @Override
    public void updateEntity(Department department) throws ResourceNotFoundException{

        Department d = getEntityById(department.getId());

        if (d == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSSAGE);

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET " + unique + " = ? WHERE id = ?;")) {

                ps.setString(1, department.getTitle());
                ps.setInt(2, department.getId());

                try {
                    ps.executeUpdate();
                    System.out.println("updated");
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

    }

    @Override
    public Department getEntityById(int id) {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?;")) {

                ps.setInt(1, id);

                try {
                    ResultSet rs = ps.executeQuery();

                    rs.next();
                    return new Department(rs.getInt(ID), rs.getString(TITLE));

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

        return null;
    }

    @Override
    public List<Department> getAllEntitiesWhere(String title) {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE title = ?;")) {

                ps.setString(1, title);

                try {
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        Department department = new Department(rs.getInt(ID), rs.getString(TITLE));
                        departmentList.add(department);
                    }

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

        return departmentList;
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
