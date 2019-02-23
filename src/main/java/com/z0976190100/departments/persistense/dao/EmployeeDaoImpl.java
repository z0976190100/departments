package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.AppRuntimeException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl extends AbstractDao implements DaoAlt<Employee>, GeneralConstants {

    @Override
    public Employee saveEntity(String email, int departmentID) {

        Employee newEmployee = null;

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO employee (email, department_id , id) VALUES ( ?, ?,  DEFAULT) RETURNING *;")) {

                ps.setString(1, email);
                ps.setInt(2, departmentID);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("saved");
                    resultSet.next();
                    newEmployee = new Employee(resultSet.getInt(ID), resultSet.getString(EMAIL_PARAM), resultSet.getInt(DEPARTMENT_ID_PARAM));
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
    public void deleteEntity(int id) {


        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM employee WHERE id = ?;")) {

                ps.setInt(1, id);

                try {
                    ps.executeUpdate();
                    System.out.println("deleted employee");
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
    public void updateEntity(Employee entity) {

    }

    @Override
    public Employee getEntityById(int id) {

        Employee employee = null;

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM employee WHERE id = ?;")) {

                ps.setInt(1, id);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("fetched one");
                    if(resultSet.next())
                        employee = new  Employee(resultSet.getInt(ID),
                                resultSet.getString(EMAIL_PARAM),
                                resultSet.getInt(DEPARTMENT_ID_PARAM));
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
        return employee;

    }

    @Override
    public List<Employee> getAllEntitiesWhere(String email) {
       List<Employee>  employees = new ArrayList<>();

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM employee WHERE email = ?;")) {

                ps.setString(1, email);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("fetched one by email");
                    while (resultSet.next())
                        employees.add(new  Employee(resultSet.getInt(ID),
                                resultSet.getString(EMAIL_PARAM),
                                resultSet.getInt(DEPARTMENT_ID_PARAM)));
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
        return employees;
    }

    @Override
    public List<Employee> getEntitiesList(int departmentID) {


        List<Employee> employeeList = new ArrayList<>();


        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM employee WHERE department_id = ?;")) {

                ps.setInt(1, departmentID);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("fetched all");
                    while (resultSet.next())
                        employeeList.add(new Employee(resultSet.getInt(ID),
                                resultSet.getString(EMAIL_PARAM),
                                resultSet.getInt(DEPARTMENT_ID_PARAM)));
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
        return employeeList;
    }

    @Override
    public List<Employee> getEntitiesList(int departmentID, int offset, int limit) {
        List<Employee> employeeList = new ArrayList<>();


        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM employee WHERE department_id = ?" +
                    " ORDER BY id" +
                    " LIMIT ? OFFSET ?;")) {

                ps.setInt(1, departmentID);
                ps.setInt(2, limit);
                ps.setInt(3, offset);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    System.out.println("fetched all with limit");
                    while (resultSet.next())
                        employeeList.add(new Employee(resultSet.getInt(ID),
                                resultSet.getString(EMAIL_PARAM),
                                resultSet.getInt(DEPARTMENT_ID_PARAM)));
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
        return employeeList;
    }

    public void deleteAllEntitiesWhere(int departmentID){

        try (Connection connection = getNullsafeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM employee WHERE department_id = ?;")) {

                ps.setInt(1, departmentID);

                try {
                    ps.executeUpdate();
                    System.out.println("deleted employees by department ID");
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
    public int getRowCount(int departmentID) {

        int rowCount = 0;

        try (Connection connection = getNullsafeConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM employee WHERE department_id = ?;")) {
                ps.setInt(1, departmentID);

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
                //TODO init fails
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new AppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
        return rowCount;
    }
}
