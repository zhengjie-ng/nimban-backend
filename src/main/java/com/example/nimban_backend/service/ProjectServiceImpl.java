package com.example.nimban_backend.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.exception.ProjectNotFoundException;
import com.example.nimban_backend.repository.CustomerRepository;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.example.nimban_backend.repository.TaskRepository;

// projectServiceImpl bean is a type of ProjectService
@Primary
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskColumnRepository taskColumnRepository;
    private final TaskRepository taskRepository;
    private final CustomerRepository customerRepository;

    public ProjectServiceImpl(
        ProjectRepository projectRepository,
        TaskRepository taskRepository,
        TaskColumnRepository taskColumnRepository,
        CustomerRepository customerRepository // new!
    ) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskColumnRepository = taskColumnRepository;
        this.customerRepository = customerRepository;
    }

    // CRUD
    // Create
    @Override
    public Project createProject(Project project) {
        
        Project savedProject = projectRepository.save(project);
        Long newProjectId = savedProject.getId();

        
        for (Long teammateId : project.getTeammatesId()) {
            customerRepository.findById(teammateId).ifPresent(customer -> {
                List<Long> existingProjects = customer.getProjectsId();

                if (existingProjects == null) {
                    existingProjects = new java.util.ArrayList<>();
                }

                if (!existingProjects.contains(newProjectId)) {
                    existingProjects.add(newProjectId);
                    customer.setProjectsId(existingProjects);
                    customerRepository.save(customer);
                }
            });
        }

        return savedProject;
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

        // Remove project ID from all customers' projects list
        List<Customer> allCustomers = customerRepository.findAll();
        for (Customer customer : allCustomers) {
            List<Long> projects = customer.getProjectsId();
            if (projects != null && projects.contains(id)) {
                projects.remove(id);               // Remove deleted project ID
                customer.setProjectsId(projects);  // Update list
                customerRepository.save(customer); // Save changes
            }
        }

        // Finally delete the project entity
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
