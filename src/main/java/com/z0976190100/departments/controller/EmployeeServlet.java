package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.exceptions.NotUniqueEntityException;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.service.EmployeeService;
import com.z0976190100.departments.service.util.AppError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmployeeServlet extends HttpServlet implements GeneralConstants {

    EmployeeService employeeService = new EmployeeService();
    List<String> success = new ArrayList<>();

    // KUNG-FUSION: and what about doOptions() ?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = getCommand(req);
        initErrorSuccessAttr(req);

        switch (command){

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
                req.getRequestDispatcher(EMPLOYEE_ADD_EDIT_JSP).forward(req, resp);
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
                }catch (NumberFormatException e){
                    e.printStackTrace();

                } catch (NotUniqueEntityException e) {
                    e.printStackTrace();
                    ((List<AppError>) req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME)).add(new AppError(EMAIL_PARAM, e.getMessage()));
                    int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
                    req.getRequestDispatcher("employees?command=get&department_id=" + departmentID).forward(req, resp);
                } catch (RequestParameterValidationException e) {
                    e.printStackTrace();
                    int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
                    req.getRequestDispatcher("employees?command=get&department_id=" + departmentID).forward(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
                resp.sendRedirect("departments?command=get&id=" + departmentID);
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
        if (req.getAttribute(ERRORS_LIST_ATTRIBUTE_NAME) == null){
            List<AppError> errors = new ArrayList<>();
            req.setAttribute(ERRORS_LIST_ATTRIBUTE_NAME, errors);
        }
        if (req.getAttribute(SUCCESS_ATTRIBUTE_NAME) == null)
            req.setAttribute(SUCCESS_ATTRIBUTE_NAME, success);
    }
}
