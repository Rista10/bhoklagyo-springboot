package com.backend.bhoklagyo.exception;

public class NoSuchCustomerExistsException extends RuntimeException {
    public NoSuchCustomerExistsException(String message) {
        super(message);
    }
}
