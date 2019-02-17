package com.z0976190100.departments.controller.command;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.persistense.entity.Employee;
import com.z0976190100.departments.service.EmployeeService;
import com.z0976190100.departments.service.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum EmployeeCommandsEnum implements GeneralConstants {

    SAVE() {
        @Override
        public Employee execute(HttpServletRequest req, HttpServletResponse res) {
            String email = req.getParameter(EMAIL_PARAM);
            // TODO validation
            new Validator().isValidEmail(email);
            return new EmployeeService().saveEmployee(email);

        }
    },
    GET {
        public Employee execute(HttpServletRequest req, HttpServletResponse res) {
return null;
        }
    },
    GET_ALL {
        @Override
        public Employee execute(HttpServletRequest req, HttpServletResponse res) {
return null;
        }
    },
    UPDATE {
        @Override
        public Employee execute(HttpServletRequest req, HttpServletResponse res) {
return null;
        }
    },
    DELETE {
        @Override
        public Employee execute(HttpServletRequest req, HttpServletResponse res) {
return null;
        }
    };

    public abstract Employee execute(HttpServletRequest req, HttpServletResponse res);

}
