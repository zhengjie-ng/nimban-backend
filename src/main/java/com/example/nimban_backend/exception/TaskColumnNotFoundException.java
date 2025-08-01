package com.example.nimban_backend.exception;

public class TaskColumnNotFoundException extends RuntimeException {
    public TaskColumnNotFoundException(Long id) {
        super("TaskColumn not found with ID: " + id);
    }

    public TaskColumnNotFoundException(String message) {
        super(message);
    }
}