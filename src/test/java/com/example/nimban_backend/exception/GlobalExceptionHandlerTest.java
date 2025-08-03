package com.example.nimban_backend.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Test
    @DisplayName("Returns 404 when customer not found")
    void shouldReturn404ForMissingCustomer() throws Exception {
        mockMvc.perform(get("/customers/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Customer with ID 9999 not found")));
    }

    @Test
    @DisplayName("Handles validation error with proper message")
    void shouldHandleValidationError() throws Exception {
        String invalidJson = """
                {
                    "firstName": "",
                    "lastName": "",
                    "email": "invalidEmail",
                    "password": "weak"
                }
                """;

        mockMvc.perform(post("/customers")
                .contentType(JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("Email should be valid")));
    }

    // @Test
    // @DisplayName("Handles IllegalArgumentException from controller")
    // void shouldHandleIllegalArgument() throws Exception {
    // mockMvc.perform(get("/projects?limit=-1"))
    // .andExpect(status().isBadRequest())
    // .andExpect(jsonPath("$.message").value(containsString("Invalid argument")));
    // }

    @Test
    @DisplayName("Handles unexpected RuntimeException gracefully")
    void shouldHandleUnexpectedException() throws Exception {
        mockMvc.perform(get("/customers/crash"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Something went wrong. Please contact the adminstrator."));
    }
}