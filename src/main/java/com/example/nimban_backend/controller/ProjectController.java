package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        try {
            Project newProject = projectService.createProject(project);
            logger.info("🟢 {} {} POST Project Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST Project Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping
    // public ResponseEntity<Project> createProject(@Valid @RequestBody Project
    // project) {
    // Project newProject = projectService.createProject(project);
    // return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    // }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> allProjects = projectService.getAllProjects();
            logger.info("🟢 {} {} GET Projects Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(allProjects, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Projects Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping
    // public ResponseEntity<List<Project>> getAllProjects() {
    // List<Project> allProjects = projectService.getAllProjects();
    // return new ResponseEntity<>(allProjects, HttpStatus.OK);
    // }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        try {
            Project foundProject = projectService.getProject(id);
            logger.info("🟢 {} {} GET Project/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(foundProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Project/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Project> getProject(@PathVariable Long id) {
    // Project foundProject = projectService.getProject(id);
    // return new ResponseEntity<>(foundProject, HttpStatus.OK);

    // }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            logger.info("🟢 {} {} PUT Project/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PUT Project/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid
    // @RequestBody Project project) {

    // Project updatedProject = projectService.updateProject(id, project);
    // return new ResponseEntity<>(updatedProject, HttpStatus.OK);

    // }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Project> patchProject(@PathVariable Long id, @RequestBody Project updates) {
        try {
            Project patchedProject = projectService.patchProject(id, updates);
            logger.info("🟢 {} {} PATCH Project/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(patchedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PATCH Project/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Project> patchProject(@PathVariable Long id,
    // @RequestBody Project updates) {
    // Project patchedProject = projectService.patchProject(id, updates);
    // return new ResponseEntity<>(patchedProject, HttpStatus.OK);
    // }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            logger.info("🟢 {} {} DELETE Project/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} DELETE Project/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long id) {
    // projectService.deleteProject(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    // }

    // NESTED ROUTE - add column to project
    @PostMapping("/{id}/taskColumns")
    public ResponseEntity<TaskColumn> addColumnToProject(@PathVariable Long id,
            @Valid @RequestBody TaskColumn taskColumn) {
        try {
            TaskColumn newTaskColumn = projectService.addColumnToProject(id, taskColumn);
            logger.info("🟢 {} {} POST Project/{}/taskColumns Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST Project/{}/taskColumns Error Exception {}", LocalDate.now(), LocalTime.now(),
                    id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/{id}/taskColumns")
    // public ResponseEntity<TaskColumn> addColumnToProject(@PathVariable Long id,
    // @Valid @RequestBody TaskColumn taskColumn) {
    // TaskColumn newTaskColumn = projectService.addColumnToProject(id, taskColumn);
    // return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    // }

    // NESTED ROUTE - add task to project
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Task> addTaskToProject(@PathVariable Long id, @Valid @RequestBody Task task) {
        try {
            Task newTask = projectService.addTaskToProject(id, task);
            logger.info("🟢 {} {} POST Project/{}/tasks Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST Project/{}/tasks Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/{id}/tasks")
    // public ResponseEntity<Task> addTaskToProject(@PathVariable Long id,
    // @Valid @RequestBody Task task) {
    // Task newTask = projectService.addTaskToProject(id, task);
    // return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    // }

}