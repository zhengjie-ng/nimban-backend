package com.example.nimban_backend.controller;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskColumnControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskColumnRepository taskColumnRepository;

    private Project project;
    private TaskColumn existingColumn;

    @BeforeEach
    void setup() {
        taskColumnRepository.deleteAll();
        projectRepository.deleteAll();

        project = projectRepository.save(Project.builder()
            .name("Test Project")
            .teammatesId(List.of(101L))
            .hidden(false)
            .taskTotalId(123L)
            .authorId(456L)
            .build());

        existingColumn = taskColumnRepository.save(TaskColumn.builder()
            .name("Existing Column")
            .position(0)
            .project(project)
            .build());
    }

    @Test
    void shouldCreateTaskColumnWithNestedProject() throws Exception {
        Project nestedProject = projectRepository.save(Project.builder()
            .name("Nested Project")
            .teammatesId(List.of(201L))
            .hidden(false)
            .taskTotalId(321L)
            .authorId(654L)
            .build());

        String requestJson = """
        {
            "name": "Column With Full Project",
            "position": 1,
            "project": { "id": %d }
        }
        """.formatted(nestedProject.getId());

        mockMvc.perform(post("/taskColumns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Column With Full Project"));
    }

    @Test
    void shouldGetAllTaskColumns() throws Exception {
        mockMvc.perform(get("/taskColumns"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetSingleTaskColumn() throws Exception {
        mockMvc.perform(get("/taskColumns/{id}", existingColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Existing Column"));
    }

    @Test
    void shouldUpdateTaskColumnWithPut() throws Exception {
        existingColumn.setName("Updated Column");
        existingColumn.setPosition(2);
        existingColumn.setProject(project); // preserve project binding

        mockMvc.perform(put("/taskColumns/{id}", existingColumn.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingColumn)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Column"));
    }

    @Test
    void shouldPatchTaskColumn() throws Exception {
        String patchJson = """
        {
            "name": "Patched Column",
            "position": 0,
            "project": { "id": %d }
        }
        """.formatted(project.getId());

        mockMvc.perform(patch("/taskColumns/{id}", existingColumn.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Patched Column"));
    }

    @Test
    void shouldDeleteTaskColumn() throws Exception {
        mockMvc.perform(delete("/taskColumns/{id}", existingColumn.getId()))
            .andExpect(status().isNoContent());

        assertThat(taskColumnRepository.findById(existingColumn.getId())).isEmpty();
    }
}