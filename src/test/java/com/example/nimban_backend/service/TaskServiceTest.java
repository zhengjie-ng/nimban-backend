package com.example.nimban_backend.service;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.exception.TaskNotFoundException;
import com.example.nimban_backend.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task mockTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Project mockProject = Project.builder().id(1L).name("Mock Project").build();

        mockTask = Task.builder()
                .id(1L)
                .name("Sample Task")
                .code("T123")
                .priority(3)
                .statusId(10L)
                .description("Task Description")
                .position(1)
                .assigneesId(Arrays.asList(100L, 200L))
                .project(mockProject)
                .build();
    }

    @Test
    void shouldCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task created = taskService.createTask(mockTask);

        assertEquals("Sample Task", created.getName());
        verify(taskRepository).save(mockTask);
    }

    @Test
    void shouldGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        Task found = taskService.getTask(1L);

        assertEquals(1L, found.getId());
        assertEquals("Sample Task", found.getName());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(2L));
    }

    @Test
    void shouldReturnAllTasks() {
        List<Task> mockTasks = Arrays.asList(mockTask);
        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Sample Task", result.get(0).getName());
    }

    @Test
    void shouldUpdateTask() {
        Task updatedTask = Task.builder()
                .name("Updated Task")
                .priority(4)
                .statusId(20L)
                .position(2)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Task", result.getName());
        assertEquals(4, result.getPriority());
        assertEquals(20L, result.getStatusId());
        assertEquals(2, result.getPosition());
    }

    @Test
    void shouldPatchTaskFields() {
        Task patch = new Task();
        patch.setName("Patched Name");
        patch.setPriority(5);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = taskService.patchTask(1L, patch);

        assertEquals("Patched Name", result.getName());
        assertEquals(5, result.getPriority());
        assertEquals("T123", result.getCode()); // unchanged
    }

    @Test
    void shouldDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}
