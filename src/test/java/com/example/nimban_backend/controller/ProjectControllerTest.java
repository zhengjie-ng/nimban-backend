package com.example.nimban_backend.controller;

import com.example.nimban_backend.dto.TaskRequestDto;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
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
class ProjectControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private TaskColumnRepository columnRepository;

    private Project existingProject;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
        columnRepository.deleteAll();
        projectRepository.deleteAll();

        existingProject = projectRepository.save(Project.builder()
            .name("Alpha")
            .teammatesId(List.of(1001L))
            .hidden(false)
            .taskTotalId(999L)
            .authorId(888L)
            .build());
    }

    @Test
    void shouldCreateProject() throws Exception {
        Project project = Project.builder()
            .name("New Project")
            .teammatesId(List.of(2001L, 2002L))
            .hidden(false)
            .taskTotalId(555L)
            .authorId(444L)
            .build();

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("New Project"));
    }

    @Test
    void shouldGetProjectById() throws Exception {
        mockMvc.perform(get("/projects/{id}", existingProject.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alpha"));
    }

    @Test
    void shouldListAllProjects() throws Exception {
        mockMvc.perform(get("/projects"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Alpha"));
    }

    @Test
    void shouldUpdateProjectWithPut() throws Exception {
        existingProject.setName("Updated Alpha");

        mockMvc.perform(put("/projects/{id}", existingProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingProject)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Alpha"));
    }

    @Test
    void shouldPatchProject() throws Exception {
        String patchJson = "{\"name\": \"Patched Alpha\", \"teammatesId\": [1001], \"hidden\": false, \"taskTotalId\": 999, \"authorId\": 888}";

        mockMvc.perform(patch("/projects/{id}", existingProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Patched Alpha"));
    }

    @Test
    void shouldDeleteProject() throws Exception {
        mockMvc.perform(delete("/projects/{id}", existingProject.getId()))
            .andExpect(status().isNoContent());

        assertThat(projectRepository.findById(existingProject.getId())).isEmpty();
    }

    @Test
    void shouldAddTaskToProject() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setName("Task A");
        dto.setCode("A-001");
        dto.setDescription("Task for Alpha");
        dto.setPriority(1L);
        dto.setStatusId(1L);
        dto.setPosition(0);
        dto.setAssigneesId(List.of(123L));
        dto.setSortedTimeStamp("2025-07-29T15:30:00");

        mockMvc.perform(post("/projects/{id}/tasks", existingProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Task A"));
    }

}