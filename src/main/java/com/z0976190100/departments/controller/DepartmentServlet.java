package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.DepartmentCommandsEnum;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.DepartmentValidator;

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
    private DepartmentValidator departmentValidator = new DepartmentValidator();
    private int actualPage = 1;
    private int limit = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command1 = req.getParameter(COMMAND_PARAM);
        DepartmentCommandsEnum command = getCommand(req);

        switch (command) {
                // getting department by id
            case GET:
                try {
                    command.execute(req);

                    req.getRequestDispatcher(GET_ALL_EMPLOYEES_URI + req.getParameter(ID)).include(req, resp);
                    req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);

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
            case GET_ALL:
                try {
                    //TODO: set default if null
                    req.setAttribute(ACTUAL_PAGE_PARAM, getInitParameter(ACTUAL_PAGE_PARAM));
                    req.setAttribute(PAGE_SIZE_LIMIT_PARAM, getInitParameter(PAGE_SIZE_LIMIT_PARAM));
                    command.execute(req);
                    req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);
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
                    break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter(COMMAND_PARAM);
        String departmentTitle = req.getParameter(DEPARTMENT_NEW_TITLE_PARAM);

        switch (command) {
            case "save":
                try {
                    departmentValidator.isValidDepartmentTitle(departmentTitle);
                    departmentService.saveDepartment(departmentTitle);
                    resp.setStatus(201);
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

            case "get":
                this.doGet(req, resp);
                break;

            default:
                resp.sendRedirect(DEPARTMENTS_URL);
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String departmentTitle = req.getParameter(DEPARTMENT_NEW_TITLE_PARAM);

        try {
            int id = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            departmentValidator.isValidDepartmentTitle(departmentTitle);
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

    private void forwardWithError(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Exception e,
                                  int sc,
                                  String error_message) throws ServletException, IOException {

        e.printStackTrace();
        resp.setStatus(sc);
        req.setAttribute(ERRORS_ATTRIBUTE_NAME, error_message);
        req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);
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

    private int paginationHelper(int rl) {

        int rc = departmentService.getRowCount();
        if(rc == 0) return 1;
        int p = rc / rl;
        if (rc % rl != 0) p++;
        return p;
    }

    private DepartmentCommandsEnum getCommand(HttpServletRequest req) {
        return DepartmentCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }

}
