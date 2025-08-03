package com.example.nimban_backend.exception;

import org.junit.jupiter.api.DisplayName;
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
class ProjectNotFoundExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Returns 404 when project is not found")
    void shouldReturn404ForMissingProject() throws Exception {
        long nonexistentId = 9999L;

        mockMvc.perform(get("/projects/{id}", nonexistentId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(
                containsString("Project not found with ID: " + nonexistentId)))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
