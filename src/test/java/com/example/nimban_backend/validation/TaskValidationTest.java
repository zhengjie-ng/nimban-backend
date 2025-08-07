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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;

@SpringBootTest
public class TaskValidationTest {

    private static final Logger logger = LoggerFactory.getLogger(TaskValidationTest.class);
    private Validator validator;

    @BeforeEach
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTask() {
        Task task = Task.builder()
                .name("Design API")
                .assigneesId(List.of(1L, 2L))
                .code("T001")
                .priority(3)
                .statusId(1L)
                .description("Designing RESTful endpoints")
                .sortedTimeStamp("2025-08-03T10:00:00")
                .position(0)
                .project(new Project())
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).isEmpty();
        logger.info("✅ testValidTask passed");
    }

    @Test
    public void testInvalidTaskNameTooShort() {
        Task task = Task.builder()
                .name("A")
                .priority(2)
                .statusId(1L)
                .position(0)
                .project(new Project())
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        logger.info("✅ testInvalidTaskNameTooShort passed");
    }

    @Test
    public void testInvalidPriorityOutOfBounds() {
        Task task = Task.builder()
                .name("Bug Fix")
                .priority(10)
                .statusId(1L)
                .position(0)
                .project(new Project())
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("priority"));
        logger.info("✅ testInvalidPriorityOutOfBounds passed");
    }

    @Test
    public void testNullFieldsShouldFail() {
        Task task = new Task();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("priority"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("statusId"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("position"));
        logger.info("✅ testNullFieldsShouldFail passed");
    }

    @Test
    public void testInvalidCodeTooLong() {
        Task task = Task.builder()
                .name("Implement Feature")
                .assigneesId(List.of(1L))
                .code("TOO_LONG_CODE_123")
                .priority(3)
                .statusId(2L)
                .position(1)
                .project(new Project())
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("code"));
        logger.info("✅ testInvalidCodeTooLong passed");
    }
}