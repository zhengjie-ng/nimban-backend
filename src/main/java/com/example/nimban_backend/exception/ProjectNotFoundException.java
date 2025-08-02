package com.example.nimban_backend.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Project not found with ID: " + id);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}