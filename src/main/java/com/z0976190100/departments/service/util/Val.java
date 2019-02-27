package com.z0976190100.departments.service.util;

import com.z0976190100.departments.app_constants.GeneralConstants;
import javafx.animation.SequentialTransition;

import javax.sql.rowset.serial.SerialStruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Val implements GeneralConstants {


    private final Map<String, List<String>> errors;

    public Val(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    private boolean isEmpty(String paramName, String paramValue) {

        if (paramValue.equals("")) {
            addError(paramName, EMPTY_PARAM_VALUE_MESSAGE);
            System.out.println("empty " + paramName);
            return true;
        }

        return false;
    }

    public Val isValidDepartmentID(String id) {

        if (isEmpty(DEPARTMENT_ID_PARAM, id)) return this;

        try {
            int departmentID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            addError(DEPARTMENT_ID_PARAM, DEPARTMENT_ID_NOT_VALID_MESSAGE);
            return this;
        }
        return this;
    }


    public Val isValidEmployeeName(String name) {

        if (isEmpty(NAME_PARAM, name)) return this;

        // valid if name has letters but no numbers, symbols except "-"
        String regex = "^[A-Za-z-\\s]+$";

        if (!checkWithRegexp(name, regex))
            addError(NAME_PARAM, EMPLOYEE_NAME_NOT_VALID_MESSAGE);

        return this;
    }

    public Val isValidEmployeeAge(String age) {

        System.out.println("is valid age");

        if (isEmpty(AGE_PARAM, age)) return this;

        try {
            int age1 = Integer.parseInt(age);

//            Instant instant = birthDate.toInstant();
//            System.out.println("------------------");
//
//            ZoneId defaultZoneId = ZoneId.systemDefault();
//            LocalDate localBD = instant.atZone(defaultZoneId).toLocalDate();
//            LocalDate now = LocalDate.now();
//            LocalDate bDbyAge = now.minusYears(age1);
//
//            System.out.println("!!!!!!!!!!!!!!!!!");
//
//            System.out.println(bDbyAge + " " + localBD);
//
//            if (localBD.getYear() != bDbyAge.getYear())

        } catch (NumberFormatException e) {
            e.printStackTrace();
            addError(AGE_PARAM, EMPLOYEE_AGE_NOT_VALID_MESSAGE);
            return this;
        }
            return this;
    }


    public Val isValidEmail(String email) {

        if (isEmpty(EMAIL_PARAM, email)) return this;

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        if (!checkWithRegexp(email, regex))
            addError(EMAIL_PARAM, EMPLOYEE_EMAIL_NOT_VALID_MESSAGE);

        return this;

    }

    public Val isValidDate(String date) {

        if (isEmpty(BIRTH_DATE_PARAM, date)) return this;

        SimpleDateFormat formatter = new SimpleDateFormat(BIRTH_DATE_PATTERN);

        try {
            Date bDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            addError(BIRTH_DATE_PARAM, BIRTH_DATE_NOT_VALID_MESSAGE);
        } finally {
            return this;
        }
    }

    public boolean hasErrors() {
        return this.getErrors().size() != 0;
    }

    public boolean hasErrors(String param) {
        return this.getErrors().get(param) != null;
    }

    private boolean checkWithRegexp(String toValidate, String regex) {
        Pattern emailRegexp = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexp.matcher(toValidate);
        return matcher.find();
    }

    private void addError(String paramName, String message) {
        if (errors.get(paramName) == null)
            errors.put(paramName, new ArrayList<String>());
        List<String> er = errors.get(paramName);
        er.add(message);
        errors.put(paramName, er);
    }

    private Map<String, List<String>> getErrors() {
        return errors;
    }
}



