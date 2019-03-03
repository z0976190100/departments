package com.z0976190100.departments.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeValidator extends AbstractValidator {

    protected final Map<String, List<String>> errors;

    public EmployeeValidator(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return this.getErrors().size() != 0;
    }

    public boolean hasErrors(String param) {
        return this.getErrors().get(param) != null;
    }

    protected void addError(String paramName, String message) {
        errors.computeIfAbsent(paramName, e -> new ArrayList<>());
        List<String> er = errors.get(paramName);
        er.add(message);
        errors.put(paramName, er);
    }

    private Map<String, List<String>> getErrors() {
        return errors;
    }

    protected boolean isEmpty(String paramName, String paramValue) {

        if (paramValue == null || paramValue.equals("")) {
            addError(paramName, EMPTY_PARAM_VALUE_MESSAGE);
            return true;
        }
        return false;
    }

    public EmployeeValidator isValidId(String id) {

        if (isEmpty(DEPARTMENT_ID_PARAM, id)) return this;

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            addError(DEPARTMENT_ID_PARAM, DEPARTMENT_ID_NOT_VALID_MESSAGE);
            return this;
        }
        return this;
    }


    public EmployeeValidator isValidEmployeeName(String name) {

        if (isEmpty(NAME_PARAM, name)) return this;

        // valid if name has letters but no numbers, symbols except "-"
        String regex = "^[A-Za-z-\\s]+$";

        if (checkWithRegexp(name, regex))
            addError(NAME_PARAM, EMPLOYEE_NAME_NOT_VALID_MESSAGE);

        return this;
    }

    public EmployeeValidator isValidEmployeeAge(String age) {

        System.out.println("is valid age");

        if (isEmpty(AGE_PARAM, age)) return this;

        try {
            Integer.parseInt(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            addError(AGE_PARAM, EMPLOYEE_AGE_NOT_VALID_MESSAGE);
            return this;
        }
        return this;
    }


    public EmployeeValidator isValidEmail(String email) {

        if (isEmpty(EMAIL_PARAM, email)) return this;

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        if (checkWithRegexp(email, regex))
            addError(EMAIL_PARAM, EMPLOYEE_EMAIL_NOT_VALID_MESSAGE);

        return this;

    }

    public EmployeeValidator isValidDate(String date) {

        // TODO: add constraints validation

        if (isEmpty(BIRTH_DATE_PARAM, date)) return this;

        SimpleDateFormat formatter = new SimpleDateFormat(BIRTH_DATE_PATTERN);

        try {
            formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            addError(BIRTH_DATE_PARAM, BIRTH_DATE_NOT_VALID_MESSAGE);
            return this;
        }
        return this;
    }
}



