package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.DepartmentCommandEnum;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.DepartmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DepartmentServlet extends HttpServlet implements GeneralConstants {

    private static Logger logger = LoggerFactory.getLogger(DepartmentServlet.class);
    private DepartmentService departmentService = new DepartmentService();
    private DepartmentValidator departmentValidator = new DepartmentValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DepartmentCommandEnum command = getCommand(req);

        switch (command) {
            case GET:
                logger.debug("{} launches.", command.toString());
                try {
                    command.execute(req);

                    req.getRequestDispatcher(GET_ALL_EMPLOYEES_URI + req.getParameter(ID)).include(req, resp);
                    req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);

                } catch (NumberFormatException e) {
                    logger.error(e.getMessage(), e);
                    proceedWithError(req, resp, 400, e.getMessage());

                } catch (ResourceNotFoundException e) {
                    logger.error(e.getMessage(), e);
                    proceedWithError(req, resp, 404, e.getMessage());

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    resp.sendError(500);
                }
                break;
            case GET_ALL:
                logger.debug("{} launches.", command.toString());
                try {
                    setPaginationAttr(req);
                    command.execute(req);
                    req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);

                } catch (NumberFormatException e) {
                    logger.error(e.getMessage(), e);
                    proceedWithError(req, resp, 400, e.getMessage());

                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    proceedWithError(req, resp, 500, DB_CONNECTION_FAILURE_MESSAGE);

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    resp.sendError(500);
                }
                break;
            default:
                logger.debug("Default case in doGet launches.");
                try {
                    setPaginationAttr(req);
                    DepartmentCommandEnum.GET_ALL.execute(req);
                    req.getRequestDispatcher(DEPARTMENTS_JSP).forward(req, resp);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DepartmentCommandEnum command = getCommand(req);

        switch (command) {
            case SAVE:
                logger.debug("{} launches.", command.toString());
                try {
                    command.execute(req);
                    resp.setStatus(201);
                    resp.sendRedirect(DEPARTMENTS_URI);
                } catch (RequestParameterValidationException e) {

                    req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
                    logger.error(e.getMessage(), e);
                    proceedWithError(req, resp, 400, e.getMessage());

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(SMTH_WRONG_MESSAGE);
                }
                break;

            case UPDATE:
                logger.debug("{} launches.", command.toString());
                this.doPut(req, resp);
                break;

            case DELETE:
                logger.debug("{} launches.", command.toString());
                this.doDelete(req, resp);
                break;

            default:
                logger.debug("Default case in doPost launches.");
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
            resp.sendRedirect(DEPARTMENTS_URI);

        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            proceedWithError(req, resp, 400, e.getMessage());

        } catch (RequestParameterValidationException e) {
            req.setAttribute(DEPARTMENT_NEW_TITLE_PARAM, req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));
            logger.error(e.getMessage(), e);
            proceedWithError(req, resp, 400, e.getMessage());

        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage(), e);
            proceedWithError(req, resp, 404, e.getMessage());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(SMTH_WRONG_MESSAGE);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = Integer.valueOf(req.getParameter(DEPARTMENT_ID_PARAM));
            departmentService.deleteDepartment(id);
            resp.setStatus(204);
            resp.sendRedirect(DEPARTMENTS_URI);

        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            proceedWithError(req, resp, 400, e.getMessage());

        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage(), e);
            proceedWithError(req, resp, 404, e.getMessage());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(SMTH_WRONG_MESSAGE);
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
                                  int sc,
                                  String error_message) throws ServletException, IOException {

        resp.setStatus(sc);
        req.setAttribute(ERRORS_ATTRIBUTE_NAME, error_message);
        this.doGet(req, resp);
    }

    private DepartmentCommandEnum getCommand(HttpServletRequest req) {
        if (req.getParameter(COMMAND_PARAM) == null || req.getParameter(COMMAND_PARAM).equals(""))
            return DepartmentCommandEnum.NO_COMMAND;
        return DepartmentCommandEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }
}
