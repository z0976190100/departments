package com.z0976190100.departments.controller.command;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.ResourceNotFoundException;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.EmployeeService;
import com.z0976190100.departments.service.util.AppError;
import com.z0976190100.departments.service.util.ValidatorAlt;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public enum EmployeeCommandsEnum implements GeneralConstants {

    SAVE() {
        @Override
        public void execute(HttpServletRequest req) throws NumberFormatException {
            String email = req.getParameter(EMAIL_PARAM);
            int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            req.setAttribute(ERRORS_ATTRIBUTE_NAME, new ValidatorAlt(new ArrayList<AppError>())
                    .isValidEmail(email)
                    .getErrors());
            req.setAttribute(EMPLOYEE_RESOURCE_KEY, new EmployeeService().saveEmployee(email, departmentID));

        }
    },
    GET {
        public void execute(HttpServletRequest req) {

        }
    },
    GET_ALL {
        @Override
        public void execute(HttpServletRequest req) throws ResourceNotFoundException {

            int actualPage = 1;
            int limit = 3;

            int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            new DepartmentService().getDepartmentById(departmentID);
            int pages = paginationHelper(limit, departmentID);
            req.setAttribute(PAGES_PARAM, pages);
            System.out.println("pages " + pages);
            if(req.getParameter(ACTUAL_PAGE_PARAM) != null)
                actualPage = Integer.parseInt(req.getParameter(ACTUAL_PAGE_PARAM));
            req.setAttribute(ACTUAL_PAGE_PARAM, actualPage);
            req.setAttribute(EMPLOYEES_LIST_PARAMETER, new EmployeeService().getAllEmployees(departmentID, limit * (actualPage-1), limit ));



        }

        private int paginationHelper(int rl, int dID){
            int rc = new EmployeeService().getRowCount(dID);
            System.out.println("count " + rc);
            if(rc == 0) return 1;
            int p = rc / rl;
            if (rc % rl != 0) p++;
            return p;
        }
    },
    UPDATE {
        @Override
        public void execute(HttpServletRequest req) {

        }
    },
    DELETE {
        @Override
        public void execute(HttpServletRequest req) {

        }
    };

    public abstract void  execute(HttpServletRequest req) throws ResourceNotFoundException;

}
