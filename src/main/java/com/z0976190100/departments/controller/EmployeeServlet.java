package com.z0976190100.departments.controller;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeServlet extends HttpServlet implements GeneralConstants {

    EmployeeService employeeService = new EmployeeService();

    // KUNG-FUSION: and what about doOptions() ?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = getCommand(req);
        try {
            command.execute(req);
        } catch (ResourceNotFoundException e) {
           e.printStackTrace();}
        req.getRequestDispatcher(DEPARTMENT_EMPLOYEES_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeCommandsEnum command = getCommand(req);

        switch (command) {
            case SAVE:
                try {
                    command.execute(req);
                } catch (ResourceNotFoundException e) {

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
        } catch (ResourceNotFoundException e){
            e.printStackTrace();
        }
    }

    private EmployeeCommandsEnum getCommand(HttpServletRequest req) {
        return EmployeeCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }
}
