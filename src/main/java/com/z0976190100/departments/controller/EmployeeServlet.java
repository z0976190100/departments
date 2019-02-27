package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    command.execute(req);
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (RequestParameterValidationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);
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
                    List<String> er = new ArrayList<>();
                    er.add(e.getMessage());
                    ((Map<String, List<String>>) req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME)).put(EMAIL_PARAM, er);

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
}
