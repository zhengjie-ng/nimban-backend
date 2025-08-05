package com.example.nimban_backend.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.nimban_backend.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // Handle CustomerNotFoundException
  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
        LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // Handle ProjectNotFoundException
  @ExceptionHandler(ProjectNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // Handle TaskNotFoundException
  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // Handle TaskColumnNotFoundException
  @ExceptionHandler(TaskColumnNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTaskColumnNotFoundException(TaskColumnNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // Validation Exception Handler
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
    List<ObjectError> validationErrors = e.getBindingResult().getAllErrors();

    StringBuilder sb = new StringBuilder();
    for (ObjectError error : validationErrors) {
      sb.append(error.getDefaultMessage()).append(". ");
    }

    String errorMessage = sb.toString().trim();
    logger.warn("🔴 Validation failed: {} | Total errors: {}", errorMessage, validationErrors.size());

    ErrorResponse errorResponse = new ErrorResponse(sb.toString().trim(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
    logger.warn("⚠️ IllegalArgumentException: {}", e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // Handle all other exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    // logger.error(e.getMessage)
    // Return a generic error message
    logger.error("🔴 Unhandled exception occurred: {}", e.getMessage(), e); // includes stack trace
    ErrorResponse errorResponse = new ErrorResponse("Something went wrong. Please contact the adminstrator.",
        LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
