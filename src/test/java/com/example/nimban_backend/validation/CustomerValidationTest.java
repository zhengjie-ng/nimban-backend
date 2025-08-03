package com.example.nimban_backend.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.Customer;

public class CustomerValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCustomer() {
        Customer customer = Customer.builder()
            .firstName("Alice")
            .lastName("Tan")
            .email("alice.tan@example.com")
            .password("Str0ng@Pass")
            .build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).isEmpty();
    }

    @Test
    void testBlankFields() {
        Customer customer = Customer.builder()
            .firstName("")
            .lastName("")
            .email("")
            .password("")
            .build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("firstName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lastName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    void testInvalidEmailFormat() {
        Customer customer = Customer.builder()
            .firstName("Bob")
            .lastName("Lee")
            .email("not-an-email")
            .password("Str0ng@Pass")
            .build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void testWeakPassword() {
        Customer customer = Customer.builder()
            .firstName("Clara")
            .lastName("Ng")
            .email("clara.ng@example.com")
            .password("weakpass") // Missing uppercase, number, special char
            .build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    void testPasswordTooShort() {
        Customer customer = Customer.builder()
            .firstName("Dan")
            .lastName("Foo")
            .email("dan.foo@example.com")
            .password("S@1a") // valid pattern but < 8 characters
            .build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }
}
