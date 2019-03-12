package com.z0976190100.departments.app_constants;

public interface GeneralConstants {


    //
    // DB
    //

    // COLUMN NAMES
    String ID = "id";
    String TITLE = "title";

    // DB CREDENTIALS
    String DB_DRIVER_NAME = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql://ec2-107-21-99-237.compute-1.amazonaws.com:5432/ddf0kqeqpgrkmg?ssl=true&sslmode=require&sslfactory=org.postgresql.ssl.NonValidatingFactory&loggerLevel=DEBUG`";
    String DB_LOGIN = "fpfuqtlhklliab";
    String DB_PASS = "49f63a9b49e09179822117a2f92e59d5e0769036dfb750bdd06f13ab3b466701";

    //
    // MESSAGES
    //

    // ERRORS AND EXCEPTIONS
    String DB_CONNECTION_FAILURE_MESSAGE = "Connection to DB is failed. ";
    String RESOURCE_NOT_FOUND_MESSAGE = "Resource Not Found. ";
    String EMPTY_PARAM_VALUE_MESSAGE = "Parameter is empty. ";
    String SMTH_WRONG_MESSAGE = "Smth went wrong...";

    // DEPARTMENT
    String DEPARTMENT_ID_NOT_VALID_MESSAGE = "Department ID you provided is invalid. ";
    String DEPARTMENT_TITLE_NOT_VALID_MESSAGE = "Title you provided not valid. Try another one. ";
    String DEPARTMENT_TITLE_TOO_LONG_MESSAGE = "Title you provided is longer than 22 characters. ";
    String DEPARTMENT_NOT_FOUND_MESSAGE = "Department not found. ";
    String DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE = "Department already exists. Try another title. ";

    // EMPLOYEE
    String EMPLOYEE_EMAIL_NOT_VALID_MESSAGE = "Email is not valid. ";
    String EMPLOYEE_EMAIL_NOT_UNIQUE_MESSAGE = "Email you provided already exist. Try another one. ";
    String EMPLOYEE_NAME_NOT_VALID_MESSAGE = "Name contains not acceptable symbols.";
    String EMPLOYEE_AGE_NOT_VALID_MESSAGE = "Check age value or format considering birth date. ";
    String BIRTH_DATE_NOT_VALID_MESSAGE = "Date not valid. ";
    String EMPLOYEE_NAME_TOO_LONG_MESSAGE = "Name is too long. ";

    //
    // HTTP REQUEST-RESPONSE PARAMETERS NAMES
    //

    // GENERAL
    String ACTUAL_PAGE_PARAM = "page";
    String PAGE_SIZE_LIMIT_PARAM = "limit";
    String PAGES_PARAM = "pages";
    String COMMAND_PARAM = "command";
    String MIN_DATE_ATTR = "min_date";
    String MAX_DATE_ATTR = "max_date";
    String BIRTH_DATE_PATTERN = "yyyy-MM-dd";

    // DEPARTMENT
    String DEPARTMENT_RESOURCE_KEY = "department";
    String DEPARTMENT_ID_PARAM = "department_id";
    String DEPARTMENT_NEW_TITLE_PARAM = "department_new_title";
    String DEPARTMENTS_LIST_PARAM = "departments_list";
    String SQL_NOT_UNIQUE_ERROR_PATTERN = "duplicate key value violates unique constraint";

    // EMPLOYEE
    String EMPLOYEE_RESOURCE_KEY = "employee";
    String EMAIL_PARAM = "email";
    String AGE_PARAM = "age";
    String NAME_PARAM = "name";
    String BIRTH_DATE_PARAM = "birth_date";
    String EMPLOYEES_LIST_PARAMETER = "employees_list";

    // EVENTS
    String ERRORS_ATTRIBUTE_NAME = "errors";
    String ERRORS_LIST_ATTRIBUTE_NAME = "errorsList";


    //
    // RESOURCE IDENTIFIERS
    //

    // JSPs
    String DEPARTMENTS_JSP = "departments.jsp?command=get_all";
    String DEPARTMENT_EMPLOYEES_JSP = "department_employees.jsp";
    String EMPLOYEE_EDIT_JSP = "employee_edit.jsp";
    String EMPLOYEE_ADD_JSP = "employee_add.jsp";
    String WELCOME_PAGE_JSP = "index.jsp";
    String ERROR_JSP = "error.jsp";

    // URIs
    String GET_DEPARTMENT_URI = "departments?command=get&id=";
    String EMPLOYEES_URI = "employees";
    String DEPARTMENTS_URI = "departments?command=get_all";
    String GET_ALL_EMPLOYEES_URI = "employees?command=get_all&department_id=";
}
