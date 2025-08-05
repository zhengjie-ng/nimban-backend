package com.example.nimban_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNotFoundException.class);

    public CustomerNotFoundException(Long customerId) {
        super("Customer with ID " + customerId + " not found");
        logger.warn("🔴 CustomerNotFoundException triggered for ID: {}", customerId);
    }

    public CustomerNotFoundException(String message) {
        super(message);
        logger.warn("🔴 CustomerNotFoundException triggered: {}", message);
    }
}