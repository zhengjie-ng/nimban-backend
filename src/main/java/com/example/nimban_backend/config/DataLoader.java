package com.example.nimban_backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.repository.CustomerRepository;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.example.nimban_backend.repository.TaskRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    private final TaskRepository taskRepository;

    private final TaskColumnRepository taskColumnRepository;

    private ProjectRepository projectRepository;
    private CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository, ProjectRepository projectRepository,
            TaskColumnRepository taskColumnRepository, TaskRepository taskRepository) {
        this.customerRepository = customerRepository;
        this.projectRepository = projectRepository;
        this.taskColumnRepository = taskColumnRepository;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void loadData() {
        // Clear the database here
        // customerRepository.deleteAll();

        // Load customers data
        customerRepository.save(Customer.builder().firstName("Alan").lastName("Walker")
                .email("alan@nimban.com").password("Alan@1234").projectsId(Arrays.asList(1L, 2L)).birthYear(1980).birthMonth(1).birthDay(1).build());
        customerRepository.save(Customer.builder().firstName("Becky").lastName("Sim")
                .email("becky@nimban.com").password("Becky@1234").projectsId(Arrays.asList(3L, 1L)).birthYear(1980).birthMonth(1).birthDay(1).build());
        customerRepository.save(Customer.builder().firstName("Cindy").projectsId(Arrays.asList(4L, 2L)).lastName("Lope")
                .email("cindy@nimban.com").password("Cindy@1234").birthYear(1980).birthMonth(1).birthDay(1).build());
        customerRepository.save(Customer.builder().firstName("Danny").lastName("Smith")
                .email("danny@nimban.com").password("Danny@1234").projectsId(Arrays.asList(5L)).birthYear(1980).birthMonth(1).birthDay(1).build());

        Project project1 = projectRepository.save(Project.builder().name("Monday").taskTotalId(4L).authorId(1L).hidden(false).teammatesId(Arrays.asList(1L, 2L)).build());
        Project project2 = projectRepository.save(Project.builder().name("Tuesday").taskTotalId(1L).authorId(1L).hidden(false).teammatesId(Arrays.asList(1L, 2L, 3L)).build());
        Project project3 = projectRepository.save(Project.builder().name("Monday").authorId(2L).taskTotalId(2L).hidden(false).teammatesId(Arrays.asList(2L)).build());
        Project project4 = projectRepository.save(Project.builder().name("Friday").authorId(3L).taskTotalId(3L).hidden(false).teammatesId(Arrays.asList(3L)).build());
        Project project5 = projectRepository.save(Project.builder().name("Sunday").authorId(4L).taskTotalId(5L).hidden(false).teammatesId(Arrays.asList(4L)).build());

        taskColumnRepository.save(TaskColumn.builder().name("Not Started").project(project1).position(0).build());
        taskColumnRepository.save(TaskColumn.builder().name("In Progress").project(project1).position(1).build());
        taskColumnRepository.save(TaskColumn.builder().name("Done").project(project1).position(2).build());
        taskColumnRepository.save(TaskColumn.builder().name("Not Started").project(project2).position(0).build());
        taskColumnRepository.save(TaskColumn.builder().name("In Progress").project(project2).position(1).build());
        taskColumnRepository.save(TaskColumn.builder().name("Done").project(project2).position(2).build());
        taskColumnRepository.save(TaskColumn.builder().name("Not Started").project(project3).position(0).build());
        taskColumnRepository.save(TaskColumn.builder().name("In Progress").project(project3).position(1).build());
        taskColumnRepository.save(TaskColumn.builder().name("Done").project(project3).position(2).build());
        taskColumnRepository.save(TaskColumn.builder().name("Not Started").project(project4).position(0).build());
        taskColumnRepository.save(TaskColumn.builder().name("In Progress").project(project4).position(1).build());
        taskColumnRepository.save(TaskColumn.builder().name("Done").project(project4).position(2).build());
        taskColumnRepository.save(TaskColumn.builder().name("Not Started").project(project5).position(0).build());
        taskColumnRepository.save(TaskColumn.builder().name("In Progress").project(project5).position(1).build());
        taskColumnRepository.save(TaskColumn.builder().name("Done").project(project5).position(2).build());

        taskRepository.save(Task.builder().name("Do laundry").project(project1).priority(1)
                .description("To wash all white clothes").statusId(1L).code("MON-0001").assigneesId(List.of(1L)).position(0).build());

        taskRepository.save(Task.builder().name("Wash dishes").project(project1).priority(2)
                .description("Wash with detergent").statusId(1L).code("MON-0002").assigneesId(List.of(1L)).position(1).build());
                
        taskRepository.save(Task.builder().name("Mop floor").project(project1).priority(3)
                .description("Mop only the living room").statusId(1L).code("MON-0003").assigneesId(List.of(1L)).position(2).build());

        taskRepository.save(Task.builder().name("Take out trash").project(project1).priority(4)
                .description("Clear trash before 7pm").statusId(2L).code("MON-0004").assigneesId(List.of(1L)).position(3).build());

        taskRepository.save(Task.builder().name("Buy grocery").project(project2).priority(1)
                .description("Get rice, milk and bread").statusId(4L).code("TUE-0001").assigneesId(List.of(1L)).position(0).build());

    }
}
