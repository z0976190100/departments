package com.z0976190100.departments.service.util;

import com.z0976190100.departments.app_constants.GeneralConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorAlt implements GeneralConstants {

    private final Map<String, String> errors;

    public ValidatorAlt(Map<String, String> errors) {
        this.errors = errors;
    }

    public ValidatorAlt isEmpty(String paramName, String paramValue){

        if(paramValue.equals(""))
            errors.put(paramName, EMPTY_PARAM_VALUE_MESSAGE);

        return this;
    }

    public ValidatorAlt isValidEmployeeName(String name) {

        // valid if name has letters but no numbers, symbols except "-"
        String regex = "^[A-Za-z-\\s]+$";

        if (!checkWithRegexp(name, regex))
            errors.put(NAME_PARAM, EMPLOYEE_NAME_NOT_VALID_MESSAGE);

        return this;
    }

    public ValidatorAlt isValidEmployeeAge(String age, Date birthDate){

        System.out.println("is valid age");



        try{
            int age1 = Integer.parseInt(age);

            Instant instant = birthDate.toInstant();
            System.out.println("------------------");

            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localBD = instant.atZone(defaultZoneId).toLocalDate();
            LocalDate now = LocalDate.now();
            LocalDate bDbyAge = now.minusYears(age1);

            System.out.println("!!!!!!!!!!!!!!!!!");

            System.out.println(bDbyAge + " " + localBD);

            if(localBD.getYear() != bDbyAge.getYear())
                errors.put(AGE_PARAM, EMPLOYEE_AGE_NOT_VALID_MESSAGE);

        }catch (NumberFormatException e){
            e.printStackTrace();
            errors.put(AGE_PARAM, EMPLOYEE_AGE_NOT_VALID_MESSAGE);
        }finally {
            return this;
        }
    }


    public ValidatorAlt isValidEmail(String toValidate) {

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        if (!checkWithRegexp(toValidate, regex))
            errors.put(EMAIL_PARAM, EMPLOYEE_EMAIL_NOT_VALID_MESSAGE);

        return this;

    }

    public ValidatorAlt isValidDate(String date) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date bDate = formatter.parse(date);
                    java.sql.Date sqlDate = new java.sql.Date(bDate.getTime());

            } catch (ParseException e) {
                e.printStackTrace();
                errors.put(BIRTH_DATE_PARAM, BIRTH_DATE_NOT_VALID_MESSAGE);
            }finally {
                return this;
            }
    }

    private boolean checkWithRegexp(String toValidate, String regex) {
        Pattern emailRegexp = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexp.matcher(toValidate);
        return matcher.find();
    }

    private Map<String, String> getErrors() {
        return errors;
    }

    public boolean hasErrors(){
        return this.getErrors().size() != 0;
    }
}
