package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.persistense.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

public class EmployeeServlet extends HttpServlet implements GeneralConstants {

    // KUNG-FUSION: and what about doOptions() ?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = EmployeeCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());

        switch (command){
            case SAVE:
                Employee employee = command.execute(req, resp);
                req.setAttribute(EMPLOYEE_RESOURCE_KEY, employee);
                req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);
                break;
            case UPDATE:
                this.doPut(req, resp);
                break;
                default:
                    break;

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
