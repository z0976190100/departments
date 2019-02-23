package com.z0976190100.departments.controller.filter;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.service.util.Validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFilter implements Filter, GeneralConstants {


        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;

            setDateBoundry(req);

            if (request.getParameter(BIRTH_DATE_PARAM) != null) {
                Validator validator = new Validator();

                String bdate = req.getParameter(BIRTH_DATE_PARAM);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date bDate = formatter.parse(bdate);
                    if (validator.isValidDate(bDate)){
                        java.sql.Date sqlDate = new java.sql.Date(bDate.getTime());
                        req.setAttribute(BIRTH_DATE_PARAM, sqlDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            chain.doFilter(request, response);
        }

        private void setDateBoundry(HttpServletRequest req){

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now();
            LocalDate lowestDateBoundry = date.minusYears(100);
            String minDate = dateFormat.format(lowestDateBoundry);
            LocalDate higestDateBoundry = date.minusYears(18);
            String maxDate = dateFormat.format(higestDateBoundry);

            req.setAttribute(MIN_DATE_ATTR, minDate);
            req.setAttribute(MAX_DATE_ATTR, maxDate);

        }


}
