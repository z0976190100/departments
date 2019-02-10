package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.General;
import com.z0976190100.departments.app_constants.URLs;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.z0976190100.departments.app_constants.Messages.DB_CONNECTION_FAILURE_MESSAGE;
import static com.z0976190100.departments.app_constants.ParameterNames.DEPARTMENTS_LIST_PARAM;
import static com.z0976190100.departments.app_constants.ParameterNames.DEPARTMENT_TITLE_PARAM;
import static com.z0976190100.departments.app_constants.ParameterNames.ERRORS_ATTRIBUTE_NAME;

public class DepartmentServlet extends HttpServlet implements General, URLs {

    private DepartmentService departmentService = new DepartmentService();
    private Validator validator = new Validator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forCase = "all";

        switch (forCase) {
            case "all":
                try {
                    List<Department> departmentsList = departmentService.getDepartmentsList();
                    req.setAttribute(DEPARTMENTS_LIST_PARAM, departmentsList);
                    requestDispatch(req, resp, DEPARTMENTS_JSP);
                    System.out.println("all done");
                } catch (SQLException e) {
                    forwardWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);
                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
            case "id":
                try {
                    Department department = departmentService.getDepartmentById(Integer.parseInt(req.getParameter(ID)));
                    req.setAttribute(DEPARTMENT_RESOURCE_KEY, department);
                    requestDispatch(req, resp, DEPARTMENTS_JSP);
                } catch (NumberFormatException e) {
                    forwardWithError(req, resp, e, 400, e.getMessage());
                } catch (ResourceNotFoundException e) {
                    forwardWithError(req, resp, e, 404, e.getMessage());
                } catch (SQLException e) {
                    forwardWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);
                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
            default:
                System.out.println("default case");

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String departmentTitle = req.getParameter(DEPARTMENT_TITLE_PARAM);
        try {
            validator.isValidDepartmentTitle(departmentTitle);
            Department department = departmentService.saveDepartment(departmentTitle);
            req.setAttribute(DEPARTMENT_RESOURCE_KEY, department);
            resp.setStatus(201);
            requestDispatch(req, resp, DEPARTMENTS_JSP);
        } catch (RequestParameterValidationException e) {
            forwardWithError(req, resp, e, 400, e.getMessage());
        } catch (SQLException e) {
            forwardWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    private void requestDispatch(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    private void forwardWithError(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Exception e,
                                  int sc,
                                  String error_message) throws ServletException, IOException {

        e.printStackTrace();
        resp.setStatus(sc);
        req.setAttribute(ERRORS_ATTRIBUTE_NAME, error_message);
        requestDispatch(req, resp, DEPARTMENTS_JSP);
    }
}
