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

import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.service.TaskColumnService;

@RestController
@RequestMapping("/taskColumns")
public class TaskColumnController {

    private TaskColumnService taskColumnService;

    public TaskColumnController(TaskColumnService taskColumnService) {
        this.taskColumnService = taskColumnService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TaskColumn> createTaskColumn(@RequestBody TaskColumn taskColumn) {
        TaskColumn newTaskColumn = taskColumnService.createTaskColumn(taskColumn);
        return new ResponseEntity<>(newTaskColumn, HttpStatus.CREATED);
    }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<TaskColumn>> getAllTaskColumns() {
        List<TaskColumn> allTaskColumns = taskColumnService.getAllTaskColumns();
        return new ResponseEntity<>(allTaskColumns, HttpStatus.OK);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<TaskColumn> getTaskColumn(@PathVariable Long id) {
        TaskColumn foundTaskColumn = taskColumnService.getTaskColumn(id);
        return new ResponseEntity<>(foundTaskColumn, HttpStatus.OK);

    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskColumn> updateTaskColumn(@PathVariable Long id, @RequestBody TaskColumn taskColumn) {

        TaskColumn updatedTaskColumn = taskColumnService.updateTaskColumn(id, taskColumn);
        return new ResponseEntity<>(updatedTaskColumn, HttpStatus.OK);

    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<TaskColumn> patchTaskColumn(@PathVariable Long id, @RequestBody TaskColumn updates) {
        TaskColumn patchedTaskColumn = taskColumnService.patchTaskColumn(id, updates);
        return new ResponseEntity<>(patchedTaskColumn, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTaskColumn(@PathVariable Long id) {
        taskColumnService.deleteTaskColumn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    // // NESTED ROUTE - add task to task column
    // @PostMapping("/{id}/tasks")
    // public ResponseEntity<Task> addTaskToTaskColumn(@PathVariable Long id,
    //         @RequestBody Task task) {
    //     Task newTask = taskColumnService.addTaskToTaskColumn(id, task);
    //     return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    // }

}