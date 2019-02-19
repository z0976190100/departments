package com.z0976190100.departments.service;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.DepartmentDaoImpl;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.persistense.entity.DepartmentEntityDescription;
import com.z0976190100.departments.persistense.entity.EntityDescription;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService implements GeneralConstants {

    private EntityDescription description = new DepartmentEntityDescription();
    private DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
    private final String table = description.getTableName();


    // FIXME pagination- where it belongs?
    public int getRowCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM " + table;
        return departmentDao.getRowCount(query);
    }

    public List<Department> getDepartmentsList(int offset, int limit) {

        return departmentDao.getEntitiesList(offset, limit);
    }

    public List<Department> getDepartmentsList() {

        return departmentDao.getEntitiesList();
    }

    public Department getDepartmentById(int id) throws ResourceNotFoundException {

        Department department = departmentDao.getEntityById(id);

        if (department == null)
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSSAGE);

        return department;

    }

    public void saveDepartment(String title) throws RequestParameterValidationException {

        // check if department is unique by title
        isExistByTitle(title);

        departmentDao.saveEntity(title);

    }

    public void deleteDepartment(int id) throws ResourceNotFoundException {

        //TODO: delete all EMPLOYEES

        departmentDao.deleteEntity(id);

    }

    public void updateDepartment(int id, String newTitle) throws ResourceNotFoundException, RequestParameterValidationException {

        isExistByTitle(newTitle);

        departmentDao.updateEntity(new Department(id, newTitle));

    }

       private void isExistByTitle(String title) throws RequestParameterValidationException {

        List<Department> departmentList = departmentDao.getAllEntitiesWhere(title);
        if (departmentList.size() != 0)
            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE);
    }

}
