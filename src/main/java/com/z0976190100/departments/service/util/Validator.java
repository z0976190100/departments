package com.z0976190100.departments.service.util;


import com.z0976190100.departments.app_constants.GeneralConstants;
import com.z0976190100.departments.exceptions.RequestParameterValidationException;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator implements GeneralConstants {

    public boolean isValidDepartmentTitle(String title) throws RequestParameterValidationException {

        // valid if title has letters and numbers but no symbols except "-"
        String regex = "^[A-Za-z0-9-\\s]+$";

        if(!checkWithRegexp(title, regex))
            throw new RequestParameterValidationException(DEPARTMENT_TITLE_NOT_VALID_MESSAGE);

        if(title.length() > 22) throw new RequestParameterValidationException(DEPARTMENT_TITLE_TOO_LONG_MESSAGE);

        return true;
    }


    public boolean isValidEmail(String toValidate) {

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        return !checkWithRegexp(toValidate, regex);

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

}
