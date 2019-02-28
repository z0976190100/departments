package com.z0976190100.departments.controller.command;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public enum EmployeeCommandsEnum implements GeneralConstants {

    NO_COMMAND {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

        }
    },
    SAVE {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

            String email = req.getParameter(EMAIL_PARAM);
            String name = req.getParameter(NAME_PARAM);
            Date birthDate = (Date) req.getAttribute(BIRTH_DATE_PARAM);
            int age = (Integer) req.getAttribute(AGE_PARAM);
            int departmentID = (Integer) req.getAttribute(DEPARTMENT_ID_PARAM);

            Employee employee = new Employee(0, name, birthDate, email, age, departmentID);

            req.setAttribute(EMPLOYEE_RESOURCE_KEY, new EmployeeService().saveEmployee(employee));

        }
    },
    GET {
        public void execute(HttpServletRequest req) throws Exception {
            if (req.getParameter(ID) != null) {
                int id = Integer.parseInt(req.getParameter(ID));
                req.setAttribute(EMPLOYEE_RESOURCE_KEY, new EmployeeService().getEmployee(id));
            }
        }
    },
    GET_SAVE_PAGE {
        @Override
        public void execute(HttpServletRequest req) throws Exception {
        }
    },
    GET_ALL {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

            int actualPage = Integer.parseInt((String)  req.getAttribute(ACTUAL_PAGE_PARAM));
            int limit = Integer.parseInt((String ) req.getAttribute(PAGE_SIZE_LIMIT_PARAM));

            int departmentID = Integer.parseInt(req.getParameter(DEPARTMENT_ID_PARAM));
            new DepartmentService().getDepartmentById(departmentID);
            int pages = paginationHelper(limit, departmentID);
            req.setAttribute(PAGES_PARAM, pages);
            if (req.getParameter(ACTUAL_PAGE_PARAM) != null)
                actualPage = Integer.parseInt(req.getParameter(ACTUAL_PAGE_PARAM));
            req.setAttribute(ACTUAL_PAGE_PARAM, actualPage);
            req.setAttribute(EMPLOYEES_LIST_PARAMETER, new EmployeeService().getAllEmployees(departmentID, limit * (actualPage - 1), limit));


        }

        private int paginationHelper(int rl, int dID) {
            int rc = new EmployeeService().getRowCount(dID);
            System.out.println("count " + rc);
            if (rc == 0) return 1;
            int p = rc / rl;
            if (rc % rl != 0) p++;
            return p;
        }
    },
    UPDATE {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

        }
    },
    DELETE {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

        }
    },
    DELETE_ALL {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

        }
    };

    public abstract void execute(HttpServletRequest req) throws Exception;

}
