package com.example.nimban_backend.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.exception.TaskColumnNotFoundException;
import com.example.nimban_backend.repository.TaskColumnRepository;

// taskColumnServiceImpl bean is a type of TaskColumnService
@Primary
@Service
public class TaskColumnServiceImpl implements TaskColumnService {
    private TaskColumnRepository taskColumnRepository;

    public TaskColumnServiceImpl(TaskColumnRepository taskColumnRepository) {
        this.taskColumnRepository = taskColumnRepository;

    }

    // CRUD
    // Create
    @Override
    public TaskColumn createTaskColumn(TaskColumn taskColumn) {
        return taskColumnRepository.save(taskColumn);
    }

    // Read
    // Read One
    @Override
    public TaskColumn getTaskColumn(Long id) {
        return taskColumnRepository.findById(id)
            .orElseThrow(() -> new TaskColumnNotFoundException(id));
    

        // Retrieve the taskColumn object and return
        // return foundTaskColumn;
    }

    // Read All
    @Override
    public List<TaskColumn> getAllTaskColumns() {
        List<TaskColumn> allTaskColumns = taskColumnRepository.findAll();
        return allTaskColumns;
    }

    // Update
    @Override
    public TaskColumn updateTaskColumn(Long id, TaskColumn taskColumn) {
        // Retrieve the taskColumn from the database
        TaskColumn taskColumnToUpdate = taskColumnRepository.findById(id)
            .orElseThrow(() -> new TaskColumnNotFoundException(id));
        // Update the fields of the taskColumn retrieved
        taskColumnToUpdate.setName(taskColumn.getName());
        taskColumnToUpdate.setPosition(taskColumn.getPosition());

        // Save the updated taskColumn back to the database
        return taskColumnRepository.save(taskColumnToUpdate);
    }

    // Patch
    @Override
    public TaskColumn patchTaskColumn(Long id, TaskColumn updates) {
        TaskColumn taskColumnToUpdate = getTaskColumn(id);

        if (updates.getName() != null) {
            taskColumnToUpdate.setName(updates.getName());
        }

        if (updates.getPosition() != null) {
            taskColumnToUpdate.setPosition(updates.getPosition());
        }

        return taskColumnRepository.save(taskColumnToUpdate);
    }

    // Delete
    @Override
    public void deleteTaskColumn(Long id) {
        if (!taskColumnRepository.existsById(id)) {
            throw new TaskColumnNotFoundException(id);
        }
        taskColumnRepository.deleteById(id);
    }

    // @Override
    // public Task addTaskToTaskColumn(Long id, Task task) {
    // // Retrieve the taskColumn from the database
    // TaskColumn selectedTaskColumn = taskColumnRepository.findById(id).get();
    // // Add the task to the taskcolumn
    // task.setTaskColumn(selectedTaskColumn);
    // return taskRepository.save(task);
    // }

}
