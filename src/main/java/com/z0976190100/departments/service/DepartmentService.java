package com.z0976190100.departments.service;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.dao.Dao;
import com.z0976190100.departments.persistense.dao.DepartmentDaoImpl;
import com.z0976190100.departments.persistense.entity.Department;

import java.util.List;

public class DepartmentService implements GeneralConstants {

    private Dao<Department> dao = new DepartmentDaoImpl();
    private EmployeeService employeeService = new EmployeeService();


    public int getRowCount() {
        return ((DepartmentDaoImpl)dao).getAllRowCount();
    }

    public List<Department> getDepartmentsList(int offset, int limit) {

        return dao.getAll(offset, limit);
    }

    public List<Department> getDepartmentsList() {

        return dao.getAll();
    }

    public Department getDepartmentById(int id) throws ResourceNotFoundException {

        Department department = dao.getEntityById(id);

        if (department == null)
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSAGE);

        return department;

    }

    public Department saveDepartment(String title) throws RequestParameterValidationException {

        // check if department is unique by title
        //isExistByTitle(title);

        return dao.saveEntity(new Department(0, title));

    }

    public void deleteDepartment(int id) throws ResourceNotFoundException {

        Department d = dao.getEntityById(id);

        if (d == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSAGE);

        employeeService.deleteAllEmployees(id);

        dao.deleteEntity(id);

    }

    public void updateDepartment(int id, String newTitle) throws ResourceNotFoundException, RequestParameterValidationException {

        Department d = dao.getEntityById(id);

        if (d == null) throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + DEPARTMENT_NOT_FOUND_MESSAGE);

        //isExistByTitle(newTitle);

        dao.updateEntity(new Department(id, newTitle));

    }

//       private void isExistByTitle(String title) throws RequestParameterValidationException {
//
//        List<Department> departmentList = dao.getAllWhere(title);
//        if (departmentList.size() != 0)
//            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE);
//    }

}
