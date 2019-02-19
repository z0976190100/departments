package com.z0976190100.departments.service.util;

public class AppError {

    private final String name;
    private final String message;

    public AppError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
