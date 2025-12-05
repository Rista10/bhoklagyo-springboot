package com.backend.bhoklagyo.exception;

public class NoSuchRestaurantExistsException extends RuntimeException {
    public NoSuchRestaurantExistsException(String message) {
        super(message);
    }
}
