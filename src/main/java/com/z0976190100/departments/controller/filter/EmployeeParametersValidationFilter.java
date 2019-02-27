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

                if(!val
                        .isValidDepartmentID(request.getParameter(DEPARTMENT_ID_PARAM))
                        .isValidDate(request.getParameter(BIRTH_DATE_PARAM))
                        .isValidEmail(request.getParameter(EMAIL_PARAM))
                        .isValidEmployeeName(request.getParameter(NAME_PARAM))
                        .isValidEmployeeAge(request.getParameter(AGE_PARAM))
                        .hasErrors()){
                    setBirthDateAttribute();
                    setIntAttribute(DEPARTMENT_ID_PARAM);
                    setIntAttribute(AGE_PARAM);
                    chain.doFilter(request, response);
                    break;
                }

                req.getRequestDispatcher("employees?command=get&department_id=" + request.getParameter(DEPARTMENT_ID_PARAM)).forward(req, response);
                break;
            default:
                chain.doFilter(request, response);
                break;
        }

    }

    private void setDateBoundaries(HttpServletRequest req){

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(BIRTH_DATE_PATTERN);
        LocalDate date = LocalDate.now();
        LocalDate lowestDateBoundary = date.minusYears(100);
        String minDate = dateFormat.format(lowestDateBoundary);
        LocalDate highestDateBoundary = date.minusYears(18);
        String maxDate = dateFormat.format(highestDateBoundary);

        req.setAttribute(MIN_DATE_ATTR, minDate);
        req.setAttribute(MAX_DATE_ATTR, maxDate);

    }

    private void setIntAttribute(String name){

        req.setAttribute(name,Integer.parseInt(req.getParameter(name)));

    }


    @Override
    public void destroy() {

    }

    private EmployeeCommandsEnum getCommand(HttpServletRequest req) {
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
