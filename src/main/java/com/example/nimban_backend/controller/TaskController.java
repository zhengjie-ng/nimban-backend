package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task newTask = taskService.createTask(task);
        logger.info("🟢 {} {} POST Task Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        logger.info("🟢 {} {} GET Tasks Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task foundTask = taskService.getTask(id);
        logger.info("🟢 {} {} GET Task/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(foundTask, HttpStatus.OK);
    }

    // FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        logger.info("🟢 {} {} PUT Task/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Task updates) {
        Task patchedTask = taskService.patchTask(id, updates);
        logger.info("🟢 {} {} PATCH Task/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(patchedTask, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        logger.info("🟢 {} {} DELETE Task/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}