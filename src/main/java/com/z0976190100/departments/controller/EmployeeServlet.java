package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.exceptions.AgeNotConsistentException;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.sql.Date;

public class EmployeeServlet extends HttpServlet implements GeneralConstants {

    EmployeeService employeeService = new EmployeeService();
    List<String> success = new ArrayList<>();

    // KUNG-FUSION: and what about doOptions() ?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = getCommand(req);
        initErrorSuccessAttr(req);


        switch (command) {

            case GET:
                try {
                    command.execute(req);
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (RequestParameterValidationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                req.getRequestDispatcher(EMPLOYEE_EDIT_JSP).forward(req, resp);
                break;
            case GET_ALL:
                try {
                    req.setAttribute(ACTUAL_PAGE_PARAM, getInitParameter(ACTUAL_PAGE_PARAM));
                    req.setAttribute(PAGE_SIZE_LIMIT_PARAM, getInitParameter(PAGE_SIZE_LIMIT_PARAM));
                    command.execute(req);
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (RequestParameterValidationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);
                break;
            case GET_SAVE_PAGE:
                req.getRequestDispatcher(EMPLOYEE_ADD_JSP).forward(req, resp);
                break;
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = getCommand(req);
        initErrorSuccessAttr(req);

        switch (command) {
            case SAVE:
                try {
                    command.execute(req);
                } catch (NotUniqueEntityException e) {
                    e.printStackTrace();
                    addError(req, EMAIL_PARAM, e.getMessage());

                    req.getRequestDispatcher(EMPLOYEE_ADD_JSP)
                            .forward(req, resp);
                } catch (AgeNotConsistentException e) {
                    addError(req, AGE_PARAM, e.getMessage());
                    req.getRequestDispatcher(EMPLOYEE_ADD_JSP)
                            .forward(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resp.sendRedirect("departments?command=get&id=" + req.getAttribute(DEPARTMENT_ID_PARAM));
                break;
            case UPDATE:
                this.doPut(req, resp);
                break;
            case DELETE:
                this.doDelete(req, resp);
                break;
            case GET_ALL:
                this.doGet(req, resp);
                break;
            case GET:
                this.doGet(req, resp);
            default:
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
        } catch (AgeNotConsistentException e) {
            e.printStackTrace();
            addError(req, AGE_PARAM, e.getMessage());
            req.getRequestDispatcher(EMPLOYEE_EDIT_JSP)
                    .forward(req, resp);
        }


        resp.sendRedirect("departments?command=get&id=" + req.getAttribute(DEPARTMENT_ID_PARAM));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter(ID));
            employeeService.deleteEmployee(id);
            int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            resp.sendRedirect("departments?command=get&id=" + departmentID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    private EmployeeCommandsEnum getCommand(HttpServletRequest req) {
        return EmployeeCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }

    private void initErrorSuccessAttr(HttpServletRequest req) {
        if (req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME) == null) {
            Map<String, String> errors = new HashMap<>();
            req.setAttribute(ERRORS_LIST_ATTRIBUTE_NAME, errors);
        }
        if (req.getAttribute(SUCCESS_ATTRIBUTE_NAME) == null)
            req.setAttribute(SUCCESS_ATTRIBUTE_NAME, success);
    }

    private void addError(HttpServletRequest req, String paramName, String message) {
        Map errors = (Map<String, List<String>>) req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME);
        if (errors.get(paramName) == null)
            errors.put(paramName, new ArrayList<String>());
        List<String> er = (List<String>) errors.get(paramName);
        er.add(message);
        errors.put(paramName, er);
    }
}
