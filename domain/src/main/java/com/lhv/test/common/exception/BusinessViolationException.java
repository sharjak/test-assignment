package com.lhv.test.common.exception;

public class BusinessViolationException extends RuntimeException {
    public BusinessViolationException(String message) {
        super(message);
    }
}
