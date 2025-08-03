package com.example.nimban_backend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFoundForMissingCustomer() throws Exception {
        Long nonexistentId = 9999L;

        mockMvc.perform(get("/customers/{id}", nonexistentId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(containsString("Customer with ID " + nonexistentId + " not found")))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
