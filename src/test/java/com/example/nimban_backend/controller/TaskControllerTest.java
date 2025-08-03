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

import java.util.List;

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
            .teammatesId(List.of(101L))
            .hidden(false)
            .taskTotalId(999L)
            .authorId(888L)
            .build());

        existingTask = taskRepository.save(Task.builder()
            .name("Existing Task")
            .assigneesId(List.of(123L))
            .code("T-EXIST")
            .priority(2)
            .statusId(1L)
            .description("Initial task")
            .position(0)
            .sortedTimeStamp("2025-07-29T14:00:00")
            .project(project)
            .build());
    }

    @Test
    void shouldCreateTaskViaProjectEndpoint() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setName("New Task");
        dto.setCode("T-NEW");
        dto.setDescription("Task description");
        dto.setPriority(3L); // Priority is defined as Integer but receives Long — confirm controller handles conversion
        dto.setStatusId(2L);
        dto.setPosition(1);
        dto.setAssigneesId(List.of(456L));
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
        existingTask.setPriority(4);
        existingTask.setPosition(1);
        existingTask.setStatusId(2L);
        existingTask.setCode("T-UPD");
        existingTask.setSortedTimeStamp("2025-07-30T09:00:00");
        existingTask.setAssigneesId(List.of(789L));

        mockMvc.perform(put("/tasks/{id}", existingTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingTask)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.description").value("Updated Desc"));
    }

    @Test
    void shouldPatchTask() throws Exception {
        String patchJson = """
        {
            "description": "Partially updated",
            "priority": 2,
            "position": 0,
            "statusId": 1,
            "name": "Existing Task",
            "project": { "id": %d },
            "assigneesId": [123]
        }
        """.formatted(project.getId());

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