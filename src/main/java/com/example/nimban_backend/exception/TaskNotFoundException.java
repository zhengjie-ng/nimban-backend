package com.example.nimban_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(TaskNotFoundException.class);

    public TaskNotFoundException(Long id) {
        super("Task not found with ID: " + id);
        logger.warn("🔴 TaskNotFoundException triggered for ID: {}", id);
    }

    public TaskNotFoundException(String message) {
        super(message);
        logger.warn("🔴 TaskNotFoundException triggered: {}", message);
    }
}