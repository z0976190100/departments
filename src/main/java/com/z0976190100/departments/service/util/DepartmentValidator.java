package com.z0976190100.departments.service.util;


import com.z0976190100.departments.exceptions.RequestParameterValidationException;

public class DepartmentValidator extends AbstractValidator {

    public boolean isValidDepartmentTitle(String title) throws RequestParameterValidationException {

        // valid if title has letters and numbers but no symbols except "-", length <= 22
        String regex = "^[A-Za-z0-9-\\s]+$";

        if (checkWithRegexp(title, regex))
            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_VALID_MESSAGE);

        if (title.length() > 22) throw new RequestParameterValidationException(DEPARTMENT_TITLE_TOO_LONG_MESSAGE);

        return true;
    }
}
