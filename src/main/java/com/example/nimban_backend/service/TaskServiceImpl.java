package com.example.nimban_backend.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.repository.TaskRepository;


// taskServiceImpl bean is a type of TaskService
@Primary
@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;


    }

    // CRUD
    // Create
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Read
    // Read One
    @Override
    public Task getTask(Long id) {
        Task foundTask = taskRepository.findById(id).get();

        // Retrieve the task object and return
        return foundTask;
    }

    // Read All
    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks;
    }

    // Update
    @Override
    public Task updateTask(Long id, Task task) {
        // Retrieve the task from the database
        Task taskToUpdate = taskRepository.findById(id).get();
        // Update the fields of the task retrieved
        taskToUpdate.setName(task.getName());
        taskToUpdate.setAssigneesId(task.getAssigneesId());
        taskToUpdate.setCode(task.getCode());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setStatusId(task.getStatusId());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setSortedTimeStamp(task.getSortedTimeStamp());
        taskToUpdate.setPosition(task.getPosition());

        // Save the updated task back to the database
        return taskRepository.save(taskToUpdate);
    }

    // Patch
    @Override
    public Task patchTask(Long id, Task updates) {
    Task taskToUpdate = getTask(id);
    
    if (updates.getName() != null) {
        taskToUpdate.setName(updates.getName());
    }
    if (updates.getAssigneesId() != null) {
        taskToUpdate.setAssigneesId(updates.getAssigneesId());
    }
    if (updates.getCode() != null) {
        taskToUpdate.setCode(updates.getCode());
    }
    if (updates.getPriority() != null) {
        taskToUpdate.setPriority(updates.getPriority());
    }

    if (updates.getStatusId() != null) {
        taskToUpdate.setStatusId(updates.getStatusId());
    }
    if (updates.getDescription() != null) {
        taskToUpdate.setDescription(updates.getDescription());
    }
    if (updates.getSortedTimeStamp() != null) {
        taskToUpdate.setSortedTimeStamp(updates.getSortedTimeStamp());
    }
    if (updates.getPosition() != null) {
        taskToUpdate.setPosition(updates.getPosition());
    }
    
    return taskRepository.save(taskToUpdate);
}



    // Delete
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}

