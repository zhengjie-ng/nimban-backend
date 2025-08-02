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
class TaskNotFoundExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Returns 404 when task is not found")
    void shouldReturn404ForMissingTask() throws Exception {
        long nonexistentTaskId = 4242L;

        mockMvc.perform(get("/tasks/{id}", nonexistentTaskId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(
                containsString("Task not found with ID: " + nonexistentTaskId)))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
