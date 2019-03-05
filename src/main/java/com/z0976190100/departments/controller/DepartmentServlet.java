package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.DepartmentCommandsEnum;
import com.z0976190100.departments.exceptions.SQLAppRuntimeException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.DepartmentValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DepartmentServlet extends HttpServlet implements GeneralConstants {

    private DepartmentService departmentService = new DepartmentService();
    private DepartmentValidator departmentValidator = new DepartmentValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DepartmentCommandsEnum command = getCommand(req);

        switch (command) {
            case GET:
                try {
                    command.execute(req);

                    req.getRequestDispatcher(GET_ALL_EMPLOYEES_URI + req.getParameter(ID)).include(req, resp);
                    req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);

                } catch (NumberFormatException e) {

                    proceedWithError(req, resp, e, 400, e.getMessage());

                } catch (ResourceNotFoundException e) {

                    proceedWithError(req, resp, e, 404, e.getMessage());

                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
            case GET_ALL:
                try {
                    setPaginationAttr(req);
                    command.execute(req);
                    req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);

                } catch (NumberFormatException e) {

                    proceedWithError(req, resp, e, 400, e.getMessage());

                } catch (SQLException e) {

                    proceedWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);

                } catch (Exception e) {
                    // FIXME
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;
            default:
                try {
                    setPaginationAttr(req);
                    DepartmentCommandsEnum.GET_ALL.execute(req);
                    req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DepartmentCommandsEnum command = getCommand(req);

        switch (command) {
            case SAVE:
                try {
                    command.execute(req);
                    resp.setStatus(201);
                    resp.sendRedirect(DEPARTMENTS_URL);
                } catch (RequestParameterValidationException e) {

                    throw new SQLAppRuntimeException("fuck");

                    //req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
                    //proceedWithError(req, resp, e, 400, e.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(500);
                }
                break;

            case UPDATE:
                this.doPut(req, resp);
                break;

            case DELETE:
                this.doDelete(req, resp);
                break;

            default:
                resp.setStatus(405);
                this.doGet(req, resp);
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

            proceedWithError(req, resp, e, 400, e.getMessage());

        } catch (RequestParameterValidationException e) {

            req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
            proceedWithError(req, resp, e, 400, e.getMessage());

        } catch (ResourceNotFoundException e) {

            proceedWithError(req, resp, e, 404, e.getMessage());

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
            proceedWithError(req, resp, e, 400, e.getMessage());

        } catch (ResourceNotFoundException e) {
            proceedWithError(req, resp, e, 404, e.getMessage());

        } catch (Exception e) {
            // FIXME
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    private void setPaginationAttr(HttpServletRequest req) {

        if (getInitParameter(ACTUAL_PAGE_PARAM) == null) req.setAttribute(ACTUAL_PAGE_PARAM, 1);
        req.setAttribute(ACTUAL_PAGE_PARAM, getInitParameter(ACTUAL_PAGE_PARAM));
        if (getInitParameter(PAGE_SIZE_LIMIT_PARAM) == null) req.setAttribute(PAGE_SIZE_LIMIT_PARAM, 3);
        req.setAttribute(PAGE_SIZE_LIMIT_PARAM, getInitParameter(PAGE_SIZE_LIMIT_PARAM));
    }

    private void proceedWithError(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Exception e,
                                  int sc,
                                  String error_message) throws ServletException, IOException {

        e.printStackTrace();
        resp.setStatus(sc);
        req.setAttribute(ERRORS_ATTRIBUTE_NAME, error_message);
        this.doGet(req, resp);
    }

    private DepartmentCommandsEnum getCommand(HttpServletRequest req) {
        if (req.getParameter(COMMAND_PARAM) == null || req.getParameter(COMMAND_PARAM).equals(""))
            return DepartmentCommandsEnum.NO_COMMAND;
        return DepartmentCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }
}
