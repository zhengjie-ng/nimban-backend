package com.example.nimban_backend.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found with ID: " + id);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}