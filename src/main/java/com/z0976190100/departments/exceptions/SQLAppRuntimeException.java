package com.z0976190100.departments.exceptions;

public class SQLAppRuntimeException extends RuntimeException {

    public SQLAppRuntimeException() {
    }

    public SQLAppRuntimeException(String message) {
        super(message);
    }
}
