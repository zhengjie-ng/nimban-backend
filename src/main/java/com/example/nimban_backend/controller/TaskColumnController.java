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

import jakarta.validation.Valid;

import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.service.TaskColumnService;

@RestController
@RequestMapping("/taskColumns")
public class TaskColumnController {

    private static final Logger logger = LoggerFactory.getLogger(TaskColumnController.class);
    private TaskColumnService taskColumnService;

    public TaskColumnController(TaskColumnService taskColumnService) {
        this.taskColumnService = taskColumnService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TaskColumn> createTaskColumn(@Valid @RequestBody TaskColumn taskColumn) {
        try {
            TaskColumn newTaskColumn = taskColumnService.createTaskColumn(taskColumn);
            logger.info("🟢 {} {} POST TaskColumn Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST TaskColumn Error Exception {}", LocalDate.now(), LocalTime.now(),
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping
    // public ResponseEntity<TaskColumn> createTaskColumn(@Valid @RequestBody
    // TaskColumn taskColumn) {
    // TaskColumn newTaskColumn = taskColumnService.createTaskColumn(taskColumn);
    // return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    // }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<TaskColumn>> getAllTaskColumns() {
        try {
            List<TaskColumn> allTaskColumns = taskColumnService.getAllTaskColumns();
            logger.info("🟢 {} {} GET TaskColumns Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(allTaskColumns, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET TaskColumns Error Exception {}", LocalDate.now(), LocalTime.now(),
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping
    // public ResponseEntity<List<TaskColumn>> getAllTaskColumns() {
    // List<TaskColumn> allTaskColumns = taskColumnService.getAllTaskColumns();
    // return new ResponseEntity<>(allTaskColumns, HttpStatus.OK);
    // }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<TaskColumn> getTaskColumn(@PathVariable Long id) {
        try {
            TaskColumn foundTaskColumn = taskColumnService.getTaskColumn(id);
            logger.info("🟢 {} {} GET TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(foundTaskColumn, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET TaskColumn/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<TaskColumn> getTaskColumn(@PathVariable Long id) {
    // TaskColumn foundTaskColumn = taskColumnService.getTaskColumn(id);
    // return new ResponseEntity<>(foundTaskColumn, HttpStatus.OK);

    // }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskColumn> updateTaskColumn(@PathVariable Long id,
            @Valid @RequestBody TaskColumn taskColumn) {
        try {
            TaskColumn updatedTaskColumn = taskColumnService.updateTaskColumn(id, taskColumn);
            logger.info("🟢 {} {} PUT TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(updatedTaskColumn, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PUT TaskColumn/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<TaskColumn> updateTaskColumn(@PathVariable Long id,
    // @Valid @RequestBody TaskColumn taskColumn) {

    // TaskColumn updatedTaskColumn = taskColumnService.updateTaskColumn(id,
    // taskColumn);
    // return new ResponseEntity<>(updatedTaskColumn, HttpStatus.OK);

    // }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<TaskColumn> patchTaskColumn(@PathVariable Long id, @RequestBody TaskColumn updates) {
        try {
            TaskColumn patchedTaskColumn = taskColumnService.patchTaskColumn(id, updates);
            logger.info("🟢 {} {} PATCH TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(patchedTaskColumn, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PATCH TaskColumn/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<TaskColumn> patchTaskColumn(@PathVariable Long id,
    // @RequestBody TaskColumn updates) {
    // TaskColumn patchedTaskColumn = taskColumnService.patchTaskColumn(id,
    // updates);
    // return new ResponseEntity<>(patchedTaskColumn, HttpStatus.OK);
    // }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTaskColumn(@PathVariable Long id) {
        try {
            taskColumnService.deleteTaskColumn(id);
            logger.info("🟢 {} {} DELETE TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} DELETE TaskColumn/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<HttpStatus> deleteTaskColumn(@PathVariable Long id) {
    // taskColumnService.deleteTaskColumn(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    // }

    // // NESTED ROUTE - add task to task column
    // @PostMapping("/{id}/tasks")
    // public ResponseEntity<Task> addTaskToTaskColumn(@PathVariable Long id,
    // @RequestBody Task task) {
    // Task newTask = taskColumnService.addTaskToTaskColumn(id, task);
    // return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    // }

}