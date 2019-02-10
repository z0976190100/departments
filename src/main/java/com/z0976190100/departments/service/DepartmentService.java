package com.z0976190100.departments.service;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.DaoImpl;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.persistense.entity.DepartmentEntityDescription;
import com.z0976190100.departments.persistense.entity.EntityDescription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.z0976190100.departments.app_constants.General.TITLE;
import static com.z0976190100.departments.app_constants.Messages.DEPARTMENT_NOT_FOUND_MESSSAGE;
import static com.z0976190100.departments.app_constants.Messages.RESOURCE_NOT_FOUND_MESSAGE;
import static com.z0976190100.departments.app_constants.General.ID;

public class DepartmentService {

    private EntityDescription departmentEntityDescription = new DepartmentEntityDescription();
    private DaoImpl<Department> departmentDao = new DaoImpl<>();

    private Department buildDepartmentFromResult(ResultSet r) throws SQLException {

        return new Department(
                r.getInt(ID),
                r.getString(TITLE));

    }

    public List<Department> getDepartmentsList() throws SQLException {

        String query = "SELECT * FROM " + departmentEntityDescription.getTableName() + ";";

        ResultSet resultSet = departmentDao.getEntitiesList(query);

        List<Department> departmentList = new ArrayList<>();

        while (resultSet.next()) {
            departmentList.add(new Department(resultSet.getInt(ID), resultSet.getString(TITLE)));
            System.out.println(resultSet.getInt(ID) + resultSet.getString(TITLE));
        }

        return departmentList;
    }

    public Department getDepartmentById(int id) throws ResourceNotFoundException, SQLException {

        String query = "SELECT * FROM " + departmentEntityDescription.getTableName() + " WHERE id = " + id;

        ResultSet result = departmentDao.getEntityById(query);

        System.out.println("result --- " + result);

        if (!result.next())
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSSAGE);

        Department department = buildDepartmentFromResult(result);

        result.close();

        return department;

        //throw new NullPointerException("Department Exception");
//        Department department = new Department(1, "First");
//        return department;
    }

    public Department saveDepartment(String title) throws SQLException {

        String query = "INSERT INTO " + departmentEntityDescription.getTableName() + " (title , id) VALUES ( '" + title + "', DEFAULT) RETURNING *;";

        ResultSet result = departmentDao.saveEntity(query);

        result.next();

        Department department = buildDepartmentFromResult(result);

        result.close();

        return department;
    }

}
