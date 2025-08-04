package com.example.nimban_backend.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.TaskColumn;

public class TaskColumnValidationTest {

    private Validator validator;

    @BeforeEach
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTaskColumn() {
        TaskColumn column = TaskColumn.builder()
                .name("To Do")
                .position(0)
                .project(new Project()) // stubbed
                .build();

        Set<ConstraintViolation<TaskColumn>> violations = validator.validate(column);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNameTooShort() {
        TaskColumn column = TaskColumn.builder()
                .name("A")
                .position(0)
                .project(new Project())
                .build();

        Set<ConstraintViolation<TaskColumn>> violations = validator.validate(column);
        assertThat(violations)
            .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    public void testNullPosition() {
        TaskColumn column = TaskColumn.builder()
                .name("In Progress")
                .position(null) // invalid
                .project(new Project())
                .build();

        Set<ConstraintViolation<TaskColumn>> violations = validator.validate(column);
        assertThat(violations)
            .anyMatch(v -> v.getPropertyPath().toString().equals("position"));
    }
}
