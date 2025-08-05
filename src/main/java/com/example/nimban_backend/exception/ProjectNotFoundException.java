package com.example.nimban_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ProjectNotFoundException.class);

    public ProjectNotFoundException(Long id) {
        super("Project not found with ID: " + id);
        logger.warn("🔴 ProjectNotFoundException triggered for ID: {}", id);
    }

    public ProjectNotFoundException(String message) {
        super(message);
        logger.warn("🔴 ProjectNotFoundException triggered: {}", message);
    }
}