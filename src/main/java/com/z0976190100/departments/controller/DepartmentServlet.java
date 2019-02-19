package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
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
import java.util.List;

public class DepartmentServlet extends HttpServlet implements GeneralConstants {

    private DepartmentService departmentService = new DepartmentService();
    private Validator validator = new Validator();
    private int actualPage = 1;
    private int limit = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter("command");

        switch (command) {
                // getting department by id
            case "get":
                try {
                    Department department = departmentService.getDepartmentById(Integer.parseInt(req.getParameter(ID)));
                    req.setAttribute(DEPARTMENT_RESOURCE_KEY, department);
                    String depWithEmployeesURI = EMPLOYEES_URI + "?command=get_all&" + DEPARTMENT_ID_PARAM + "=" + department.getId();
                   req.getRequestDispatcher(depWithEmployeesURI).forward(req, resp);
                } catch (NumberFormatException e) {
                    forwardWithError(req, resp, e, 400, e.getMessage());
                } catch (ResourceNotFoundException e) {
                    forwardWithError(req, resp, e, 404, e.getMessage());
                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
            case "get_all":
                try {
                    if (req.getParameter(ACTUAL_PAGE_PARAM) != null)
                        actualPage = Integer.parseInt(req.getParameter(ACTUAL_PAGE_PARAM));
                    req.setAttribute(ACTUAL_PAGE_PARAM, actualPage);
                    setListToAttributes(req, limit * (actualPage - 1), limit);
                    requestDispatch(req, resp, DEPARTMENTS_JSP);
                } catch (NumberFormatException e) {
                    forwardWithError(req, resp, e, 400, e.getMessage());
                } catch (SQLException e) {
                    forwardWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);
                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
                default:
                    resp.sendRedirect(DEPARTMENTS_JSP);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter(COMMAND_PARAM);
        String departmentTitle = req.getParameter(DEPARTMENT_NEW_TITLE_PARAM);

        switch (command) {
            case "save":
                try {
                    validator.isValidDepartmentTitle(departmentTitle);
                    departmentService.saveDepartment(departmentTitle);
                    resp.setStatus(201);
                    System.out.println("save");
                    resp.sendRedirect(DEPARTMENTS_URL);
                } catch (RequestParameterValidationException e) {
                    try {
                        setListToAttributes(req, limit * (actualPage - 1), limit);
                        req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
                    } catch (SQLException e1) {
                        forwardWithError(req, resp, e1, 500, DB_CONNECTION_FAILURE_MESSAGE);
                    }
                    forwardWithError(req, resp, e, 400, e.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;

            case "update":
                this.doPut(req, resp);
                break;

            case "delete":
                this.doDelete(req, resp);
                break;

            default:
                resp.sendRedirect(DEPARTMENTS_URL);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String departmentTitle = req.getParameter(DEPARTMENT_NEW_TITLE_PARAM);

        try {
            int id = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            validator.isValidDepartmentTitle(departmentTitle);
            departmentService.updateDepartment(id, departmentTitle);
            resp.setStatus(204);
            resp.sendRedirect(DEPARTMENTS_URL);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            forwardWithError(req, resp, e, 400, e.getMessage());
        } catch (RequestParameterValidationException e) {
            try {
                setListToAttributes(req, limit * (actualPage - 1), limit);
                req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
            } catch (SQLException e1) {
                forwardWithError(req, resp, e1, 500, DB_CONNECTION_FAILURE_MESSAGE);
            }
            forwardWithError(req, resp, e, 400, e.getMessage());
        } catch (ResourceNotFoundException e) {
            forwardWithError(req, resp, e, 404, e.getMessage());
        } catch (Exception e) {
            // FIXME
            e.printStackTrace();
            resp.sendError(500);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = Integer.valueOf(req.getParameter(DEPARTMENT_ID_PARAM));
            departmentService.deleteDepartment(id);
            resp.setStatus(204);
            resp.sendRedirect(DEPARTMENTS_URL);
        } catch (NumberFormatException e) {
            forwardWithError(req, resp, e, 400, e.getMessage());
        } catch (ResourceNotFoundException e) {
            forwardWithError(req, resp, e, 404, e.getMessage());
        } catch (Exception e) {
            // FIXME
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

    private void setListToAttributes(HttpServletRequest req, int offset, int limit) throws SQLException {
        int pages = paginationHelper(limit);
        req.setAttribute(PAGES_PARAM, pages);
        List<Department> departmentsList = departmentService.getDepartmentsList(offset, limit);
        req.setAttribute(DEPARTMENTS_LIST_PARAM, departmentsList);
    }

    private void setListToAttributes(HttpServletRequest req) {

        List<Department> departmentsList = departmentService.getDepartmentsList();
        req.setAttribute(DEPARTMENTS_LIST_PARAM, departmentsList);
    }

    private int paginationHelper(int rl) throws SQLException {

        int rc = departmentService.getRowCount();
        if(rc == 0) return 1;
        int p = rc / rl;
        if (rc % rl != 0) p++;
        return p;
    }

}
