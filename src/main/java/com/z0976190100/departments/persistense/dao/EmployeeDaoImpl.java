package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.SQLAppRuntimeException;
import com.z0976190100.departments.persistense.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl extends AbstractDao implements EmployeeDao, GeneralConstants {

    private static Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);
    private static final String SAVE_EMPLOYEE_QUERY = "INSERT INTO employee (name, email, department_id , birth_date, age, id) VALUES ( ?, ?, ?, ?, ?,  DEFAULT) RETURNING *;";
    private static final String UPDATE_EMPLOYEE_QUERY = "UPDATE employee SET name = ?, email = ?, department_id = ?, birth_date = ?, age = ? WHERE id = ? ;";
    private static final String DELETE_EMPLOYEE_QUERY = "DELETE FROM employee WHERE id = ?;";
    private static final String GET_EMPLOYEE_BY_ID_QUERY = "SELECT * FROM employee WHERE id = ?;";
    private static final String GET_EMPLOYEE_BY_EMAIL_QUERY = "SELECT * FROM employee WHERE email = ?;";
    private static final String GET_EMPLOYEE_BY_DEPARTMENTID_QUERY = "SELECT * FROM employee WHERE department_id = ?;";
    private static final String GET_EMPLOYEE_BY_DEPARTMENTID_LIMITED_QUERY = "SELECT * FROM employee WHERE department_id = ? ORDER BY id LIMIT ? OFFSET ?;";
    private static final String DELETE_EMPLOYEES_BY_DEPARTMENTID_QUERY = "DELETE FROM employee WHERE department_id = ?;";
    private static final String ROW_COUNT_BY_DEPARTMENTID_QUERY = "SELECT COUNT(*) FROM employee WHERE department_id = ?;";

    @Override
    public Employee saveEntity(Employee entity) {

        Employee newEmployee = null;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(SAVE_EMPLOYEE_QUERY)) {
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getEmail());
                ps.setInt(3, entity.getDepartmentID());
                ps.setObject(4, (Date) entity.getBirthDate());
                ps.setInt(5, entity.getAge());

                try {
                    ResultSet resultSet = ps.executeQuery();
                    resultSet.next();
                    newEmployee = buildEmployee(resultSet);
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("{} is saved successfully. ", newEmployee);
        return newEmployee;
    }

    @Override
    public int updateEntity(Employee employee) {

        int result = 0;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_EMPLOYEE_QUERY)) {
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getEmail());
                ps.setInt(3, employee.getDepartmentID());
                ps.setObject(4, (Date) employee.getBirthDate());
                ps.setInt(5, employee.getAge());
                ps.setInt(6, employee.getId());

                try {
                    result = ps.executeUpdate();

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("{} update result is [{}].", employee, result);
        return result;

    }

    @Override
    public void deleteEntity(int id) {

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(DELETE_EMPLOYEE_QUERY)) {

                ps.setInt(1, id);

                try {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("Employee{id={}} deleted successfully.", id);
    }

    @Override
    public Employee getEntityById(int id) {

        Employee employee = null;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_BY_ID_QUERY)) {

                ps.setInt(1, id);

                try {
                    ResultSet resultSet = ps.executeQuery();

                    if (resultSet.next())
                        employee = buildEmployee(resultSet);

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("{} fetched.", employee);
        return employee;

    }

    @Override
    public List<Employee> getAllWhere(String email) {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_BY_EMAIL_QUERY)) {

                ps.setString(1, email);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next())
                        employees.add(buildEmployee(resultSet));

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("[{}] Employees fetched where email{email={}}.", employees.size(), email);
        return employees;
    }

    @Override
    public List<Employee> getAllWhere(int departmentID) {

        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_BY_DEPARTMENTID_QUERY)) {

                ps.setInt(1, departmentID);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next())
                        employeeList.add(buildEmployee(resultSet));

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("[{}] Employees fetched where Department{id={}}.", employeeList.size(), departmentID);
        return employeeList;
    }

    @Override
    public List<Employee> getAllWhere(int departmentID, int offset, int limit) {
        List<Employee> employeeList = new ArrayList<>();


        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_BY_DEPARTMENTID_LIMITED_QUERY)) {

                ps.setInt(1, departmentID);
                ps.setInt(2, limit);
                ps.setInt(3, offset);

                try {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next())
                        employeeList.add(buildEmployee(resultSet));

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("[{}] Employees fetched where Department{id={}} with offset [{}].", employeeList.size(), departmentID, offset);
        return employeeList;
    }

    public void deleteAllEntitiesWhere(int departmentID) {

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(DELETE_EMPLOYEES_BY_DEPARTMENTID_QUERY)) {

                ps.setInt(1, departmentID);

                try {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("Employees with Department{id={}} deleted successfully.", departmentID);
    }

    public int getAllWhereRowCount(int departmentID) {

        int rowCount = 0;

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(ROW_COUNT_BY_DEPARTMENTID_QUERY)) {
                ps.setInt(1, departmentID);

                try {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    rowCount = rs.getInt(1);
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("ROW COUNT for Employees of Department{id={}} result is [{}].", departmentID, rowCount);
        return rowCount;
    }

    private Employee buildEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(resultSet.getInt(ID),
                resultSet.getString(NAME_PARAM),
                resultSet.getDate(BIRTH_DATE_PARAM),
                resultSet.getString(EMAIL_PARAM),
                resultSet.getInt(AGE_PARAM),
                resultSet.getInt(DEPARTMENT_ID_PARAM));
    }
}
