package com.example.codechallenge.utils;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorMessage {
    private String message;
    private int statusCode;

    private List<FieldError> fieldErrors;
    public ErrorMessage(String message) {
        this.message = message;
    }
    public ErrorMessage(String message, List<FieldError> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
    public ErrorMessage(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
