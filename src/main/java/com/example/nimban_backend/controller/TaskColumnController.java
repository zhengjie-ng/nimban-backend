package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.service.TaskColumnService;

@RestController
@RequestMapping("/taskColumns")
public class TaskColumnController {

    private static final Logger logger = LoggerFactory.getLogger(TaskColumnController.class);
    private final TaskColumnService taskColumnService;

    public TaskColumnController(TaskColumnService taskColumnService) {
        this.taskColumnService = taskColumnService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TaskColumn> createTaskColumn(@Valid @RequestBody TaskColumn taskColumn) {
        TaskColumn newTaskColumn = taskColumnService.createTaskColumn(taskColumn);
        logger.info("🟢 {} {} POST TaskColumn Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<TaskColumn>> getAllTaskColumns() {
        List<TaskColumn> allTaskColumns = taskColumnService.getAllTaskColumns();
        logger.info("🟢 {} {} GET TaskColumns Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(allTaskColumns, HttpStatus.OK);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<TaskColumn> getTaskColumn(@PathVariable Long id) {
        TaskColumn foundTaskColumn = taskColumnService.getTaskColumn(id);
        logger.info("🟢 {} {} GET TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(foundTaskColumn, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskColumn> updateTaskColumn(@PathVariable Long id,
                                                       @Valid @RequestBody TaskColumn taskColumn) {
        TaskColumn updatedTaskColumn = taskColumnService.updateTaskColumn(id, taskColumn);
        logger.info("🟢 {} {} PUT TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(updatedTaskColumn, HttpStatus.OK);
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<TaskColumn> patchTaskColumn(@PathVariable Long id, @RequestBody TaskColumn updates) {
        TaskColumn patchedTaskColumn = taskColumnService.patchTaskColumn(id, updates);
        logger.info("🟢 {} {} PATCH TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(patchedTaskColumn, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskColumn(@PathVariable Long id) {
        taskColumnService.deleteTaskColumn(id);
        logger.info("🟢 {} {} DELETE TaskColumn/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Optional: NESTED ROUTE - Add task to task column
    // @PostMapping("/{id}/tasks")
    // public ResponseEntity<Task> addTaskToTaskColumn(@PathVariable Long id,
    //                                                 @RequestBody Task task) {
    //     Task newTask = taskColumnService.addTaskToTaskColumn(id, task);
    //     return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    // }
}