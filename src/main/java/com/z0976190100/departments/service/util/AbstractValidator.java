package com.z0976190100.departments.service.util;

import com.z0976190100.departments.app_constants.GeneralConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractValidator implements GeneralConstants {

    protected boolean checkWithRegexp(String toValidate, String regex) {
        Pattern emailRegexp = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexp.matcher(toValidate);
        return !matcher.find();
    }


}
