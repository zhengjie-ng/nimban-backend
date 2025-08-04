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

import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        try {
            Task newTask = taskService.createTask(task);
            logger.info("🟢 {} {} POST Task Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST Task Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping
    // public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
    // Task newTask = taskService.createTask(task);
    // return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    // }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> allTasks = taskService.getAllTasks();
            logger.info("🟢 {} {} GET Tasks Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(allTasks, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Tasks Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping
    // public ResponseEntity<List<Task>> getAllTasks() {
    // List<Task> allTasks = taskService.getAllTasks();
    // return new ResponseEntity<>(allTasks, HttpStatus.OK);
    // }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        try {
            Task foundTask = taskService.getTask(id);
            logger.info("🟢 {} {} GET Task/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(foundTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Task/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Task> getTask(@PathVariable Long id) {
    // Task foundTask = taskService.getTask(id);
    // return new ResponseEntity<>(foundTask, HttpStatus.OK);

    // }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            logger.info("🟢 {} {} PUT Task/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PUT Task/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid
    // @RequestBody Task task) {

    // Task updatedTask = taskService.updateTask(id, task);
    // return new ResponseEntity<>(updatedTask, HttpStatus.OK);

    // }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Task updates) {
        try {
            Task patchedTask = taskService.patchTask(id, updates);
            logger.info("🟢 {} {} PATCH Task/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(patchedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PATCH Task/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody
    // Task updates) {
    // Task patchedTask = taskService.patchTask(id, updates);
    // return new ResponseEntity<>(patchedTask, HttpStatus.OK);
    // }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            logger.info("🟢 {} {} DELETE Task/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} DELETE Task/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
    // taskService.deleteTask(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    // }

}