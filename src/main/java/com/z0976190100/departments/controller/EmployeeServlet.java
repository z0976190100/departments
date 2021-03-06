package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandEnum;
import com.z0976190100.departments.exceptions.AgeNotConsistentException;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeServlet extends HttpServlet implements GeneralConstants {

    private static Logger logger = LoggerFactory.getLogger(DepartmentServlet.class);
    private EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandEnum command = getCommand(req);

        switch (command) {

            case GET:
                logger.debug("{} launches.", command.toString());
                try {
                    command.execute(req);
                } catch (ResourceNotFoundException e) {
                    logger.error(e.getMessage(), e);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(SMTH_WRONG_MESSAGE);
                }
                req.getRequestDispatcher(EMPLOYEE_EDIT_JSP).forward(req, resp);
                break;
            case GET_ALL:
                logger.debug("{} launches.", command.toString());
                try {
                    setPaginationAttr(req);
                    command.execute(req);
                } catch (ResourceNotFoundException e) {
                    logger.error(e.getMessage(), e);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(SMTH_WRONG_MESSAGE);
                }
                break;
            case GET_SAVE_PAGE:
                logger.debug("{} launches.", command.toString());
                req.getRequestDispatcher(EMPLOYEE_ADD_JSP).forward(req, resp);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandEnum command = getCommand(req);

        switch (command) {
            case SAVE:
                logger.debug("{} launches.", command.toString());
                try {
                    command.execute(req);
                } catch (NotUniqueEntityException e) {
                    logger.error(e.getMessage(), e);
                    addError(req, EMAIL_PARAM, e.getMessage());
                    req.getRequestDispatcher(EMPLOYEE_ADD_JSP)
                            .forward(req, resp);
                    break;
                } catch (AgeNotConsistentException e) {
                    logger.error(e.getMessage(), e);
                    addError(req, AGE_PARAM, e.getMessage());
                    req.getRequestDispatcher(EMPLOYEE_ADD_JSP)
                            .forward(req, resp);
                    break;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(SMTH_WRONG_MESSAGE);
                }
                resp.setStatus(201);
                resp.sendRedirect(GET_DEPARTMENT_URI + req.getAttribute(DEPARTMENT_ID_PARAM));
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
                req.getRequestDispatcher(EMPLOYEE_ADD_JSP)
                        .forward(req, resp);
                break;

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter(EMAIL_PARAM);
        String name = req.getParameter(NAME_PARAM);
        Date birthDate = (Date) req.getAttribute(BIRTH_DATE_PARAM);
        int age = (Integer) req.getAttribute(AGE_PARAM);
        int departmentID = (Integer) req.getAttribute(DEPARTMENT_ID_PARAM);
        int id = (Integer) req.getAttribute(ID);

        Employee employee = new Employee(id, name, birthDate, email, age, departmentID);

        req.setAttribute(EMPLOYEE_RESOURCE_KEY, employee);
        try {
            req.setAttribute(EMPLOYEE_RESOURCE_KEY, employeeService.updateEmployee(employee));
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage(), e);
            addError(req, RESOURCE_NOT_FOUND_MESSAGE, e.getMessage());
            req.getRequestDispatcher(EMPLOYEE_EDIT_JSP)
                    .forward(req, resp);
        } catch (AgeNotConsistentException e) {
            logger.error(e.getMessage(), e);
            addError(req, AGE_PARAM, e.getMessage());
            req.getRequestDispatcher(EMPLOYEE_EDIT_JSP)
                    .forward(req, resp);
        }

        resp.sendRedirect(GET_DEPARTMENT_URI + req.getAttribute(DEPARTMENT_ID_PARAM));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = (Integer)req.getAttribute(ID);
            employeeService.deleteEmployee(id);
            resp.sendRedirect(GET_DEPARTMENT_URI + req.getParameter(DEPARTMENT_ID_PARAM));

        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage(), e);
            addError(req, RESOURCE_NOT_FOUND_MESSAGE, e.getMessage());
            req.getRequestDispatcher(EMPLOYEE_EDIT_JSP)
                    .forward(req, resp);
        }
    }

    private EmployeeCommandEnum getCommand(HttpServletRequest req) {
        if(req.getParameter(COMMAND_PARAM) == null || req.getParameter(COMMAND_PARAM).equals(""))
            return EmployeeCommandEnum.NO_COMMAND;
        return EmployeeCommandEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }

    private void setPaginationAttr(HttpServletRequest req) {

        if(getInitParameter(ACTUAL_PAGE_PARAM) == null) req.setAttribute(ACTUAL_PAGE_PARAM, 1);
        req.setAttribute(ACTUAL_PAGE_PARAM, getInitParameter(ACTUAL_PAGE_PARAM));
        if(getInitParameter(PAGE_SIZE_LIMIT_PARAM) == null) req.setAttribute(PAGE_SIZE_LIMIT_PARAM, 3);
        req.setAttribute(PAGE_SIZE_LIMIT_PARAM, getInitParameter(PAGE_SIZE_LIMIT_PARAM));
    }


    private void addError(HttpServletRequest req, String paramName, String message) {

        Map<String, List<String>> errors = (Map<String, List<String>>) req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME);
        errors.computeIfAbsent(paramName, e -> new ArrayList<>());
        List<String> er = errors.get(paramName);
        er.add(message);
        errors.put(paramName, er);
    }
}
