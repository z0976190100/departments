package com.z0976190100.departments.controller.filter;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.controller.command.EmployeeCommandsEnum;
import com.z0976190100.departments.service.util.Val;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeParametersValidationFilter implements Filter, GeneralConstants {

    private HttpServletRequest req;

// TODO success messages

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        req = (HttpServletRequest) request;

        EmployeeCommandsEnum command = getCommand(req);

        Map<String, List<String>> errors = new HashMap<>();
        Val val = new Val(errors);
        req.setAttribute(ERRORS_LIST_ATTRIBUTE_NAME, errors);

        setDateBoundaries(req);

        switch (command) {
            case SAVE:
                if (!saveUpdateValidationChunk(val)) {
                    setAttributes();
                    chain.doFilter(req, response);
                    break;
                }
                req.getRequestDispatcher(EMPLOYEE_ADD_JSP).forward(req, response);
                break;
            case GET:
                //TODO: check id
                chain.doFilter(req, response);
                break;
            case GET_ALL:
                //TODO: check department_id
                chain.doFilter(req, response);
                break;
            case DELETE:
                //TODO: check id
                chain.doFilter(req, response);
                break;
            case UPDATE:
                if (!val.isValidId(req.getParameter(ID)).hasErrors(ID) && !saveUpdateValidationChunk(val)) {
                    setIntAttribute(ID);
                    setAttributes();
                    chain.doFilter(req, response);
                    break;
                }
                req.getRequestDispatcher(EMPLOYEE_EDIT_JSP).forward(req, response);
                break;
            case DELETE_ALL:
                //TODO: check department_id
            default:
                chain.doFilter(request, response);
                break;
        }

    }

    private void setAttributes() {
        setBirthDateAttribute();
        setIntAttribute(DEPARTMENT_ID_PARAM);
        setIntAttribute(AGE_PARAM);
    }

    private boolean saveUpdateValidationChunk(Val val) {
        return val
                .isValidId(req.getParameter(DEPARTMENT_ID_PARAM))
                .isValidDate(req.getParameter(BIRTH_DATE_PARAM))
                .isValidEmail(req.getParameter(EMAIL_PARAM))
                .isValidEmployeeName(req.getParameter(NAME_PARAM))
                 .isValidEmployeeAge(req.getParameter(AGE_PARAM))
                .hasErrors();
    }

    private void setDateBoundaries(HttpServletRequest req) {

        System.out.println("setting date boundaries");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(BIRTH_DATE_PATTERN);
        LocalDate date = LocalDate.now();
        LocalDate lowestDateBoundary = date.minusYears(100);
        String minDate = dateFormat.format(lowestDateBoundary);
        LocalDate highestDateBoundary = date.minusYears(18);
        String maxDate = dateFormat.format(highestDateBoundary);

        req.setAttribute(MIN_DATE_ATTR, minDate);
        req.setAttribute(MAX_DATE_ATTR, maxDate);

    }

    private void setIntAttribute(String name) {

        req.setAttribute(name, Integer.parseInt(req.getParameter(name)));

    }


    @Override
    public void destroy() {

    }

    private EmployeeCommandsEnum getCommand(HttpServletRequest req) {
        if(req.getParameter(COMMAND_PARAM) == null || req.getParameter(COMMAND_PARAM).equals(""))
            return EmployeeCommandsEnum.NO_COMMAND;
        return EmployeeCommandsEnum.valueOf(req.getParameter(COMMAND_PARAM).toUpperCase());
    }

    private void setBirthDateAttribute() {

        String bdate = req.getParameter(BIRTH_DATE_PARAM);
        SimpleDateFormat formatter = new SimpleDateFormat(BIRTH_DATE_PATTERN);
        try {
            Date bDate = formatter.parse(bdate);
            java.sql.Date sqlDate = new java.sql.Date(bDate.getTime());
            req.setAttribute(BIRTH_DATE_PARAM, sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO
        }
    }
}
