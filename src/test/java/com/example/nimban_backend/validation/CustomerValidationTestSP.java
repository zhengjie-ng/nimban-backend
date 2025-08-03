package com.example.nimban_backend.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerValidationTestSP {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should reject invalid customer data with 400")
    void shouldRejectInvalidCustomer() throws Exception {
        String invalidPayload = """
                        {
                  "firstName": "",
                  "lastName": "",
                  "email": "invalid-email",
                  "password": "weak123",
                  "projectsId": [1, 2, 3]
                }
                        """;

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("First name is required")))
                .andExpect(jsonPath("$.message").value(containsString("Last name is required")))
                .andExpect(jsonPath("$.message").value(containsString("Email should be valid")))
                .andExpect(jsonPath("$.message").value(containsString("Password must include")));
    }
}