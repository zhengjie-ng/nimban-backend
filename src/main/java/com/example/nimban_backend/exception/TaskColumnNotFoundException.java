package com.example.nimban_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskColumnNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(TaskColumnNotFoundException.class);

    public TaskColumnNotFoundException(Long id) {
        super("TaskColumn not found with ID: " + id);
        logger.warn("🔴 TaskColumnNotFoundException triggered for ID: {}", id);
    }

    public TaskColumnNotFoundException(String message) {
        super(message);
        logger.warn("🔴 TaskColumnNotFoundException triggered: {}", message);
    }
}