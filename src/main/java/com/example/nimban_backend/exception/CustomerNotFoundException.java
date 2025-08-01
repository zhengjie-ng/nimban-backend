package com.example.nimban_backend.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Customer with ID " + id + " not found.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}