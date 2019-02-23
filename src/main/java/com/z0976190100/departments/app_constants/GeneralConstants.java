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
    String BAD_REQUEST_MESSAGE = "Bad request or parameter. ";
    String DB_CONNECTION_FAILURE_MESSAGE = "Connection to DB is failed. ";
    String RESOURCE_NOT_FOUND_MESSAGE = "Resource Not Found. ";

    // DEPARTMENT
    String DEPARTMENT_TITLE_NOT_VALID_MESSAGE = "Title you provided not valid. Try another one. ";
    String DEPARTMENT_NOT_FOUND_MESSSAGE = "Department not found. ";
    String DEPARTMENT_TITLE_NOT_UNIQUE_MESSAGE = "Department already exists. Try another title. ";

    // EMPLOYEE
    String EMPLOYEE_EMAIL_NOT_VALID_MESSAGE = "Email is not valid";
    String EMPLOYEE_EMAIL_NOT_UNIQUE_MESSAGE = "Email you provided already exist. Try another one. ";

    //
    // HTTP REQUEST-RESPONSE PARAMETERS NAMES
    //

    // GENERAL
    String ACTUAL_PAGE_PARAM = "page";
    String PAGES_PARAM = "pages";
    String COMMAND_PARAM = "command";
    String MIN_DATE_ATTR = "min_date";
    String MAX_DATE_ATTR = "max_date";

    // DEPARTMENT
    String DEPARTMENT_TITLE_PARAM = "department_title";
    String DEPARTMENT_ID_PARAM = "department_id";
    String DEPARTMENT_NEW_TITLE_PARAM = "department_new_title";
    String DEPARTMENTS_LIST_PARAM = "departments_list";

    // EMPLOYEE
    String EMAIL_PARAM = "email";
    String AGE_PARAM = "age";
    String NAME_PARAM = "name";
    String BIRTH_DATE_PARAM = "birth_date";
    String EMPLOYEES_LIST_PARAMETER = "employees_list";

    // EVENTS
    String ERRORS_ATTRIBUTE_NAME = "errors";
    String ERRORS_LIST_ATTRIBUTE_NAME = "errorsList";
    String SUCCESS_ATTRIBUTE_NAME = "success";

    //
    // URLs
    //

    String DEPARTMENTS_URL = "/departments?command=get_all";
    String WELCOME_PAGE_URL = "/index";

    // JSPs
    String DEPARTMENTS_JSP = "departments.jsp?command=get_all";
    String DEPARTMENT_EMPLOYEES_JSP = "department_employees.jsp";
    String EMPLOYEE_ADD_EDIT_JSP = "employee_add_edit.jsp";
    String WELCOME_PAGE_JSP = "index.jsp";

    // URIs
    String DEPARTMENTS_URI = "departments";
    String EMPLOYEES_URI = "employees";
    String DEPARTMENT_RESOURCE_KEY = "department";
    String EMPLOYEE_RESOURCE_KEY = "employee";
}