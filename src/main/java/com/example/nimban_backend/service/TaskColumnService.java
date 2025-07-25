package com.example.nimban_backend.service;

import java.util.List;

import com.example.nimban_backend.entity.TaskColumn;

public interface TaskColumnService {
  TaskColumn createTaskColumn(TaskColumn taskColumn);

  TaskColumn getTaskColumn(Long id);

  List<TaskColumn> getAllTaskColumns();

  TaskColumn updateTaskColumn(Long id, TaskColumn taskColumn);

  TaskColumn patchTaskColumn(Long id, TaskColumn updates);

  void deleteTaskColumn(Long id);


}