package com.example.nimban_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.exception.ProjectNotFoundException;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.example.nimban_backend.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskColumnRepository taskColumnRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project sampleProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProject = Project.builder()
                .id(1L)
                .name("Alpha")
                .hidden(false)
                .teammatesId(Arrays.asList(100L, 200L))
                .taskTotalId(10L)
                .authorId(99L)
                .build();
    }

    @Test
    void shouldCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Project created = projectService.createProject(sampleProject);

        assertEquals("Alpha", created.getName());
        verify(projectRepository).save(sampleProject);
    }

    @Test
    void shouldGetProjectById() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        Project result = projectService.getProject(1L);

        assertEquals(1L, result.getId());
        assertEquals("Alpha", result.getName());
    }

    @Test
    void shouldThrowIfProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProject(1L));
    }

    @Test
    void shouldUpdateProject() {
        Project updates = Project.builder()
                .name("Updated")
                .teammatesId(Arrays.asList(101L))
                .hidden(true)
                .taskTotalId(20L)
                .authorId(88L)
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        Project result = projectService.updateProject(1L, updates);

        assertEquals("Updated", result.getName());
        assertEquals(101L, result.getTeammatesId().get(0));
        assertEquals(20L, result.getTaskTotalId());
    }

    @Test
    void shouldPatchProjectFields() {
        Project patch = new Project();
        patch.setName("Patched");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        Project result = projectService.patchProject(1L, patch);

        assertEquals("Patched", result.getName());
        assertEquals(Arrays.asList(100L, 200L), result.getTeammatesId()); // unchanged
    }

    @Test
    void shouldDeleteProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        projectService.deleteProject(1L);

        verify(projectRepository).delete(sampleProject);
    }
}
