package com.example.nimban_backend.service;

import java.util.List;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;

public interface ProjectService {
  Project createProject(Project project);

  Project getProject(Long id);

  List<Project> getAllProjects();

  Project updateProject(Long id, Project project);

  Project patchProject(Long id, Project updates);

  void deleteProject(Long id);

  TaskColumn addColumnToProject(Long id, TaskColumn taskColumn);

  Task addTaskToProject(Long id, Task task);

}