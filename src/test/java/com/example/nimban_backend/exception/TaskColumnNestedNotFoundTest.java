package com.example.nimban_backend.exception;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskColumnNestedNotFoundTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskColumnRepository taskColumnRepository;

    @BeforeEach
    void setup() {
        taskColumnRepository.deleteAll();
        projectRepository.deleteAll();

        projectRepository.save(Project.builder()
            .name("Test Project")
            .teammatesId(List.of(101L))
            .hidden(false)
            .taskTotalId(123L)
            .authorId(456L)
            .build());

        // Intentionally not saving any TaskColumn with ID 8888L to simulate not found
    }

    @Test
    @DisplayName("Returns 404 when task column is not found")
    void shouldReturn404ForMissingTaskColumn() throws Exception {
        long nonexistentTaskColumnId = 8888L;

        mockMvc.perform(get("/taskColumns/{id}", nonexistentTaskColumnId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(
                containsString("TaskColumn not found with ID: " + nonexistentTaskColumnId)))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
