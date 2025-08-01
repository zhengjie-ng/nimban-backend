package com.example.nimban_backend.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.exception.ProjectNotFoundException;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.example.nimban_backend.repository.TaskRepository;

// projectServiceImpl bean is a type of ProjectService
@Primary
@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private TaskColumnRepository taskColumnRepository;
    private TaskRepository taskRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository,
            TaskColumnRepository taskColumnRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskColumnRepository = taskColumnRepository;

    }

    // CRUD
    // Create
    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Read
    // Read One
     @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException(id));

        // Retrieve the project object and return
        // return foundProject;
    }

    // Read All
    @Override
    public List<Project> getAllProjects() {
        List<Project> allProjects = projectRepository.findAll();
        return allProjects;
    }

    // Update
    @Override
    public Project updateProject(Long id, Project project) {
        // Retrieve the project from the database
        Project projectToUpdate = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException(id));
        // Update the fields of the project retrieved
        projectToUpdate.setName(project.getName());
        projectToUpdate.setTeammatesId(project.getTeammatesId());
        projectToUpdate.setHidden(project.getHidden());
        projectToUpdate.setTaskTotalId(project.getTaskTotalId());
        projectToUpdate.setAuthorId(project.getAuthorId());

        // Save the updated project back to the database
        return projectRepository.save(projectToUpdate);
    }

    // Patch
     @Override
    public Project patchProject(Long id, Project updates) {
        Project projectToUpdate = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        if (updates.getName() != null) {
            projectToUpdate.setName(updates.getName());
        }
        if (updates.getTeammatesId() != null) {
            projectToUpdate.setTeammatesId(updates.getTeammatesId());
        }
        if (updates.getHidden() != null) {
            projectToUpdate.setHidden(updates.getHidden());
        }
        if (updates.getTaskTotalId() != null) {
            projectToUpdate.setTaskTotalId(updates.getTaskTotalId());
        }
        if (updates.getAuthorId() != null) {
            projectToUpdate.setAuthorId(updates.getAuthorId());
        }

        return projectRepository.save(projectToUpdate);
    }

    // Delete
    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.delete(project);
    }

    @Override
    public TaskColumn addColumnToProject(Long id, TaskColumn taskColumn) {
        Project selectedProject = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException(id));
        taskColumn.setProject(selectedProject);
        return taskColumnRepository.save(taskColumn);
    }

    @Override
    public Task addTaskToProject(Long id, Task task) {
        // Retrieve the taskColumn from the database
        Project selectedProject = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException(id));
        // Add the task to the taskcolumn
        task.setProject(selectedProject);
        return taskRepository.save(task);
    }

}
