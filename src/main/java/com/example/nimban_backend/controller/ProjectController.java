package com.example.nimban_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project newProject = projectService.createProject(project);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = projectService.getAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project foundProject = projectService.getProject(id);
        return new ResponseEntity<>(foundProject, HttpStatus.OK);

    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {

        Project updatedProject = projectService.updateProject(id, project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);

    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Project> patchProject(@PathVariable Long id, @RequestBody Project updates) {
        Project patchedProject = projectService.patchProject(id, updates);
        return new ResponseEntity<>(patchedProject, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    // NESTED ROUTE - add column to project
    @PostMapping("/{id}/taskColumns")
    public ResponseEntity<TaskColumn> addColumnToProject(@PathVariable Long id,
            @RequestBody TaskColumn taskColumn) {
        TaskColumn newTaskColumn = projectService.addColumnToProject(id, taskColumn);
        return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    }

    // NESTED ROUTE - add task to project
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Task> addTaskToProject(@PathVariable Long id,
            @RequestBody Task task) {
        Task newTask = projectService.addTaskToProject(id, task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

}