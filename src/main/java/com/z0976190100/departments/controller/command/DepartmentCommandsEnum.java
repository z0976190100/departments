package com.z0976190100.departments.controller.command;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.service.DepartmentService;
import com.z0976190100.departments.service.util.DepartmentValidator;

import javax.servlet.http.HttpServletRequest;

public enum DepartmentCommandsEnum implements GeneralConstants {

    NO_COMMAND {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

        }
    },
    SAVE {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

            new DepartmentValidator().isValidDepartmentTitle(req.getParameter(DEPARTMENT_NEW_TITLE_PARAM));

            req.setAttribute(DEPARTMENT_RESOURCE_KEY, new DepartmentService().saveDepartment(req.getParameter(DEPARTMENT_NEW_TITLE_PARAM)));

        }
    },
    GET {
        public void execute(HttpServletRequest req) throws Exception {
            if (req.getParameter(ID) != null) {
                int id = Integer.parseInt(req.getParameter(ID));
                req.setAttribute(DEPARTMENT_RESOURCE_KEY, new DepartmentService().getDepartmentById(id));
            }
        }
    },
    GET_ALL {
        @Override
        public void execute(HttpServletRequest req) throws Exception {

            int actualPage = Integer.parseInt((String) req.getAttribute(ACTUAL_PAGE_PARAM));
            int limit = Integer.parseInt((String) req.getAttribute(PAGE_SIZE_LIMIT_PARAM));

            int pages = paginationHelper(limit);
            req.setAttribute(PAGES_PARAM, pages);

            if (req.getParameter(ACTUAL_PAGE_PARAM) != null)
                actualPage = Integer.parseInt(req.getParameter(ACTUAL_PAGE_PARAM));

            req.setAttribute(ACTUAL_PAGE_PARAM, actualPage);
            req.setAttribute(DEPARTMENTS_LIST_PARAM, new DepartmentService().getDepartmentsList( limit * (actualPage - 1), limit));

        }

        private int paginationHelper(int rl) {
            int rc = new DepartmentService().getRowCount();
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
    };

    public abstract void execute(HttpServletRequest req) throws Exception;

}
