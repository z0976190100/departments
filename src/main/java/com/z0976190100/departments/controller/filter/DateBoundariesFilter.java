package com.z0976190100.departments.controller.filter;

import com.z0976190100.departments.app_constants.GeneralConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateBoundariesFilter implements Filter, GeneralConstants {


        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;

            setDateBoundaries(req);


            chain.doFilter(request, response);
        }

        private void setDateBoundaries(HttpServletRequest req){

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
