package com.example.nimban_backend.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@TestConfiguration
public class NoValidationConfig {
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean() {
            @Override
            public void validate(Object target, Errors errors, Object... validationHints) {
                // disable all validation logic
            }
        };
    }
}
