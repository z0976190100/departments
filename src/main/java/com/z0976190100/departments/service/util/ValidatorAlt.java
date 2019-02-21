package com.z0976190100.departments.service.util;

import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorAlt implements GeneralConstants {

    private final List<AppError> errors;

    public ValidatorAlt(List<AppError> errors) {
        this.errors = errors;
    }

    public ValidatorAlt isValidDepartmentTitle(String title) throws RequestParameterValidationException {

        // valid if title has letters and numbers but no symbols except "-"
        String regex = "^[A-Za-z0-9-\\s]+$";

        if (!checkWithRegexp(title, regex))
            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_VALID_MESSAGE);

        return this;
    }


    public ValidatorAlt isValidEmail(String toValidate) {

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        if (!checkWithRegexp(toValidate, regex))
            System.out.println("email error");
            errors.add(new AppError(EMAIL_PARAM, EMPLOYEE_EMAIL_NOT_VALID_MESSAGE));
        System.out.println(this);

        return this;

    }

    public boolean isValidDate(Date toValidate) {

// TODO:
        return true;
    }

    private boolean checkWithRegexp(String toValidate, String regex) {
        Pattern emailRegexp = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexp.matcher(toValidate);
        return matcher.find();
    }

    private List<AppError> getErrors() {
        return errors;
    }

    public boolean hasErrors(){
        return this.getErrors().size() != 0;
    }
}
