package com.example.nimban_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.exception.ProjectNotFoundException;
import com.example.nimban_backend.repository.CustomerRepository;
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

    @Mock
    private CustomerRepository customerRepository;

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

    // @Test
    // void shouldCreateProject() {
    // when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

    // Project created = projectService.createProject(sampleProject);

    // assertEquals("Alpha", created.getName());
    // verify(projectRepository).save(sampleProject);
    // }
    
    @Test
    void shouldCreateProject() {
        // Mock project repository save
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        // Mock customer repository behavior
        Customer teammate1 = new Customer();
        teammate1.setId(100L);
        teammate1.setProjectsId(new ArrayList<>());

        Customer teammate2 = new Customer();
        teammate2.setId(200L);
        teammate2.setProjectsId(new ArrayList<>());

        when(customerRepository.findById(100L)).thenReturn(Optional.of(teammate1));
        when(customerRepository.findById(200L)).thenReturn(Optional.of(teammate2));
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        Project created = projectService.createProject(sampleProject);

        assertEquals("Alpha", created.getName());
        verify(projectRepository).save(sampleProject);

        verify(customerRepository).findById(100L);
        verify(customerRepository).findById(200L);
        verify(customerRepository, times(2)).save(any(Customer.class));
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

    // @Test
    // void shouldDeleteProject() {
    // when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

    // projectService.deleteProject(1L);

    // verify(projectRepository).delete(sampleProject);
    // }

    @Test
    void shouldDeleteProject() {
        // Mock project repository
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        // Mock customers with this project in their list
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setProjectsId(new ArrayList<>(List.of(1L)));

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setProjectsId(new ArrayList<>(List.of(1L, 2L)));

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        projectService.deleteProject(1L);

        verify(projectRepository).delete(sampleProject);

        // Verify customers were updated
        assertFalse(customer1.getProjectsId().contains(1L));
        assertTrue(customer2.getProjectsId().contains(2L)); // other project remains
        assertFalse(customer2.getProjectsId().contains(1L));
        verify(customerRepository, times(2)).save(any(Customer.class));
    }
}
