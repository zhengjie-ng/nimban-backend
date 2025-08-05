package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project newProject = projectService.createProject(project);
        logger.info("🟢 {} {} POST Project Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = projectService.getAllProjects();
        logger.info("🟢 {} {} GET Projects Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project foundProject = projectService.getProject(id);
        logger.info("🟢 {} {} GET Project/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(foundProject, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        logger.info("🟢 {} {} PUT Project/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Project> patchProject(@PathVariable Long id, @RequestBody Project updates) {
        Project patchedProject = projectService.patchProject(id, updates);
        logger.info("🟢 {} {} PATCH Project/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(patchedProject, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        logger.info("🟢 {} {} DELETE Project/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ADD COLUMN TO PROJECT
    @PostMapping("/{id}/taskColumns")
    public ResponseEntity<TaskColumn> addColumnToProject(@PathVariable Long id, @Valid @RequestBody TaskColumn taskColumn) {
        TaskColumn newTaskColumn = projectService.addColumnToProject(id, taskColumn);
        logger.info("🟢 {} {} POST Project/{}/taskColumns Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    }

    // ADD TASK TO PROJECT
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Task> addTaskToProject(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task newTask = projectService.addTaskToProject(id, task);
        logger.info("🟢 {} {} POST Project/{}/tasks Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }
}