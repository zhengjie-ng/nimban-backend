package com.example.nimban_backend.service;

import java.util.List;

import com.example.nimban_backend.entity.Task;

public interface TaskService {
  Task createTask(Task task);

  Task getTask(Long id);

  List<Task> getAllTasks();

  Task updateTask(Long id, Task task);

  Task patchTask(Long id, Task updates);

  void deleteTask(Long id);

}