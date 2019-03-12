package com.z0976190100.departments.persistense.dao;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.SQLAppRuntimeException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.persistense.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl extends AbstractDao implements DepartmentDao, GeneralConstants {

    private static Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);
    private static final String SAVE_DEPARTMENT_QUERY = "INSERT INTO department (title , id) VALUES ( ? , DEFAULT) RETURNING *;";
    private static final String UPDATE_DEPARTMENT_QUERY = "UPDATE department SET title = ? WHERE id = ?;";
    private static final String DELETE_DEPARTMENT_QUERY = "DELETE FROM department WHERE id = ?;";
    private static final String GET_DEPARTMENT_BY_ID_QUERY = "SELECT * FROM department WHERE id = ?;";
    private static final String GET_DEPARTMENTS_LIST_QUERY = "SELECT * FROM department;";
    private static final String GET_DEPARTMENTS_LIST_LIMITED_QUERY = "SELECT * FROM department ORDER BY id LIMIT ? OFFSET ? ;";
    private static final String GET_DEPARTMENTS_BY_TITLE_QUERY = "SELECT * FROM department WHERE title = ?;";
    private static final String ALL_ROW_COUNT_QUERY = "SELECT COUNT(*) FROM department ;";

    @Override
    public Department saveEntity(Department department) throws RequestParameterValidationException {

        Department newDepartment = null;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(SAVE_DEPARTMENT_QUERY)) {

                ps.setString(1, department.getTitle());

                try {

                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()) {
                        newDepartment = new Department(resultSet.getInt(ID), resultSet.getString(TITLE));
                    }


                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                    for (Throwable t : e) {
                        if (t.getMessage().contains(SQL_NOT_UNIQUE_ERROR_PATTERN))
                            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }
        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("{} is saved successfully. ", newDepartment);
        return newDepartment;

    }

    @Override
    public int updateEntity(Department department) {

        int result = 0;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_DEPARTMENT_QUERY)) {

                ps.setString(1, department.getTitle());
                ps.setInt(2, department.getId());

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

        logger.debug("{} update result is [{}].", department, result);
        return result;
    }

    @Override
    public void deleteEntity(int id) {

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(DELETE_DEPARTMENT_QUERY)) {

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

        logger.debug("Department{id={}} deleted successfully.", id);
    }

    @Override
    public Department getEntityById(int id) {

        Department department = null;

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_DEPARTMENT_BY_ID_QUERY)) {

                ps.setInt(1, id);

                try {

                    ResultSet rs = ps.executeQuery();

                    if(rs.next())
                        department = new Department(rs.getInt(ID), rs.getString(TITLE));

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

        logger.debug("{} fetched.", department);
        return department;
    }

    @Override
    public List<Department> getAll() {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_DEPARTMENTS_LIST_QUERY)) {

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("[{}] Departments fetched.", departmentList.size());
        return departmentList;
    }

    @Override
    public List<Department> getAll(int offset, int limit) {

        List<Department> departmentList = new ArrayList<>();

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_DEPARTMENTS_LIST_LIMITED_QUERY)) {

                ps.setInt(1, limit);
                ps.setInt(2, offset);

                getEntityListHelper(departmentList, ps);

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }
        logger.debug("[{}] Departments fetched with offset [{}].", departmentList.size(), offset);
        return departmentList;
    }

    @Override
    public List<Department> getAllWhere(String title) {

        List<Department> departments = new ArrayList<>();

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(GET_DEPARTMENTS_BY_TITLE_QUERY)) {

                ps.setString(1, title);


                getEntityListHelper(departments, ps);


            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
            }

        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new SQLAppRuntimeException(DB_CONNECTION_FAILURE_MESSAGE);
        }

        logger.debug("[{}] Departments fetched with {title={}}.", departments.size(), title);
        return departments;
    }

    private void getEntityListHelper(List<Department> departmentList, PreparedStatement ps) {
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                departmentList.add(new Department(rs.getInt(ID), rs.getString(TITLE)));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int getAllRowCount() {

        int rowCount = 0;

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(ALL_ROW_COUNT_QUERY)) {

                try {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    rowCount = rs.getInt(1);
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

        logger.debug("ROW COUNT for ALL Departments result is [{}].", rowCount);
        return rowCount;
    }
}
