package com.z0976190100.departments.exceptions;

import javax.servlet.ServletException;

public class RequestParameterValidationException extends Exception {

    public RequestParameterValidationException() {
    }

    public RequestParameterValidationException(String message) {
        super(message);
    }
}
