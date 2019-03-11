package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.SQLAppRuntimeException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.persistense.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl extends AbstractDao implements DepartmentDao<Department>, GeneralConstants {

    @Override
    public Department saveEntity(Department department) throws RequestParameterValidationException {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO department (title , id) VALUES ( ? , DEFAULT);")) {

                ps.setString(1, department.getTitle());

                try {
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();

                    for (Throwable t : e) {
                        if (t.getMessage().contains(SQL_NOT_UNIQUE_ERROR_PATTERN))
                            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE);
                    }
                    //TODO init fails
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return department;

    }

    @Override
    public void updateEntity(Department department) {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("UPDATE department SET title = ? WHERE id = ?;")) {

                ps.setString(1, department.getTitle());
                ps.setInt(2, department.getId());

                try {

                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                    for (Throwable t : e) {
                        t.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

    }

    @Override
    public void deleteEntity(int id) {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM department WHERE id = ?;")) {

                ps.setInt(1, id);

                try {
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();

                    for (Throwable t : e) {
                        System.out.println(t.getMessage());
                    }
                    //TODO init fails
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
    }

    @Override
    public Department getEntityById(int id) {

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM department WHERE id = ?;")) {

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
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return null;
    }

    @Override
    public List<Department> getAll() {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM department ORDER BY id ;")) {

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return departmentList;
    }

    @Override
    public List<Department> getAll(int offset, int limit) {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM department" +
                    " ORDER BY id" +
                    " LIMIT " + limit + " OFFSET " + offset + ";")) {

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        return departmentList;
    }

    @Override
    public List<Department> getAllWhere(String title) {
        List<Department> departments = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM department WHERE title = ?;")) {

                ps.setString(1, title);


                    getEntityListHelper(departments, ps);


            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
        return departments;
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

    public int getAllRowCount() {

        int rowCount = 0;

        try (Connection connection = getNullsafeConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM department ;")) {

                try {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    rowCount = rs.getInt(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();

                for (Throwable t : e) {
                    System.out.println(t.getMessage());
                }
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
        return rowCount;
    }
}
