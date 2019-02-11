package com.z0976190100.departments.controller;

import com.z0976190100.departments.service.DepartmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.z0976190100.departments.app_constants.General.DEPARTMENTS_JSP;
import static com.z0976190100.departments.app_constants.Messages.DB_CONNECTION_FAILURE_MESSAGE;
import static com.z0976190100.departments.app_constants.ParameterNames.ERRORS_ATTRIBUTE_NAME;
import static com.z0976190100.departments.app_constants.URLs.DEPARTMENTS_URL;

public class TempDepartmentDeleteServlet extends HttpServlet {

    private DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        String[] pathSplitted = pathInfo.split("/");
        int id = Integer.valueOf(pathSplitted[pathSplitted.length-1]);
        try {
            departmentService.deleteDepartment(id);
            resp.sendRedirect(DEPARTMENTS_URL);
        } catch (NumberFormatException e) {
            forwardWithError(req, resp, e, 400, e.getMessage());
        } catch (SQLException e) {
            forwardWithError(req, resp, e, 500, DB_CONNECTION_FAILURE_MESSAGE);
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
}