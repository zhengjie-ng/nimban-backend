package com.example.nimban_backend.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.Project;

public class ProjectValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProject() {
        Project project = Project.builder()
            .name("CRM Redesign")
            .teammatesId(List.of(1L, 2L))
            .hidden(Boolean.FALSE)
            .taskTotalId(123L)
            .authorId(456L)
            .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNameTooShort() {
        Project project = Project.builder()
            .name("AB")
            .teammatesId(List.of(1L))
            .hidden(Boolean.TRUE)
            .taskTotalId(100L)
            .authorId(200L)
            .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    public void testEmptyTeammatesList() {
        Project project = Project.builder()
            .name("Growth Initiative")
            .teammatesId(List.of()) // invalid
            .hidden(Boolean.TRUE)
            .taskTotalId(10L)
            .authorId(20L)
            .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("teammatesId"));
    }

    @Test
    public void testNullHiddenFlag() {
        Project project = Project.builder()
            .name("Sprint")
            .teammatesId(List.of(10L))
            .hidden(null) // invalid
            .taskTotalId(5L)
            .authorId(1L)
            .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("hidden"));
    }

    @Test
    public void testMissingRequiredFields() {
        Project project = new Project(); // everything null

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("teammatesId"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("hidden"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("taskTotalId"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("authorId"));
    }
}