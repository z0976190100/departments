package com.z0976190100.departments.controller;

import com.z0976190100.departments.exceptions.RequestParameterValidationException;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.persistense.entity.Department;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.z0976190100.departments.app_constants.GeneralConstants.DEPARTMENTS_JSP;
import static com.z0976190100.departments.app_constants.GeneralConstants.ID;
import static com.z0976190100.departments.app_constants.GeneralConstants.TITLE;
import static com.z0976190100.departments.app_constants.MessagesConstants.DB_CONNECTION_FAILURE_MESSAGE;
import static com.z0976190100.departments.app_constants.ParameterNamesConstants.DEPARTMENTS_LIST_PARAM;
import static com.z0976190100.departments.app_constants.ParameterNamesConstants.DEPARTMENT_NEW_TITLE_PARAM;
import static com.z0976190100.departments.app_constants.ParameterNamesConstants.ERRORS_ATTRIBUTE_NAME;
import static com.z0976190100.departments.app_constants.URLsConstants.DEPARTMENTS_URL;

public class DepartmentUpdateServlet extends HttpServlet {

    private DepartmentService departmentService = new DepartmentService();
    private Validator validator = new Validator();
    private int actualPage = 1;
    private int limit = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPut(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        String[] pathSplitted = pathInfo.split("/");
        int id = Integer.valueOf(pathSplitted[pathSplitted.length - 1]);

        String departmentTitle = req.getParameter(DEPARTMENT_NEW_TITLE_PARAM);

        try {
            validator.isValidDepartmentTitle(departmentTitle);
            departmentService.updateDepartment(id, departmentTitle);
            resp.sendRedirect(DEPARTMENTS_URL);
        } catch (NumberFormatException e) {
            e.printStackTrace();
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

    private void setListToAttributes(HttpServletRequest req, int offset, int limit) throws SQLException {
        int pages = paginationHelper(limit);
        req.setAttribute("pages", pages);
        List<Department> departmentsList = departmentService.getDepartmentsList(offset, limit);
        req.setAttribute(DEPARTMENTS_LIST_PARAM, departmentsList);
    }

    private int paginationHelper(int rl) throws SQLException {

        int rc = departmentService.getRowCount();

        int p = rc / rl;

        if (rc % rl != 0) p++;

        return p;
    }
}
