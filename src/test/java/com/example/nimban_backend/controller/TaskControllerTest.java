package com.example.nimban_backend.controller;

import com.example.nimban_backend.dto.TaskRequestDto;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskRepository taskRepository;

    private Project project;
    private Task existingTask;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();

        project = projectRepository.save(Project.builder()
            .name("Test Project")
            .teammatesId(Collections.emptyList())
            .hidden(false)
            .build());

        existingTask = taskRepository.save(Task.builder()
            .name("Existing Task")
            .project(project)
            .description("Initial task")
            .code("T-EXIST")
            .priority(1L)
            .statusId(1L)
            .position(0)
            .assigneesId(Collections.singletonList(123L))
            .sortedTimeStamp("2025-07-29T14:00:00")
            .build());
    }

    @Test
    void shouldCreateTaskViaProjectEndpoint() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setName("New Task");
        dto.setCode("T-NEW");
        dto.setDescription("Task description");
        dto.setPriority(2L);
        dto.setStatusId(1L);
        dto.setPosition(1);
        dto.setAssigneesId(Collections.singletonList(456L));
        dto.setSortedTimeStamp("2025-07-29T15:00:00");

        mockMvc.perform(post("/projects/{id}/tasks", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        mockMvc.perform(get("/tasks/{id}", existingTask.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Existing Task"));
    }

    @Test
    void shouldListAllTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Existing Task"));
    }

    @Test
    void shouldFullyUpdateTaskWithPut() throws Exception {
        existingTask.setName("Updated Name");
        existingTask.setDescription("Updated Desc");

        mockMvc.perform(put("/tasks/{id}", existingTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingTask)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.description").value("Updated Desc"));
    }

    @Test
    void shouldPatchTask() throws Exception {
        String patchJson = "{\"description\": \"Partially updated\"}";

        mockMvc.perform(patch("/tasks/{id}", existingTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Partially updated"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", existingTask.getId()))
            .andExpect(status().isNoContent());

        assertThat(taskRepository.findById(existingTask.getId())).isEmpty();
    }
}