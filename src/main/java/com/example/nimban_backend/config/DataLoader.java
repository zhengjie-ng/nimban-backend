package com.example.nimban_backend.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.nimban_backend.entity.AppRole;
import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.entity.Project;
import com.example.nimban_backend.entity.Role;
import com.example.nimban_backend.entity.Task;
import com.example.nimban_backend.entity.TaskColumn;
import com.example.nimban_backend.repository.CustomerRepository;
import com.example.nimban_backend.repository.ProjectRepository;
import com.example.nimban_backend.repository.RoleRepository;
import com.example.nimban_backend.repository.TaskColumnRepository;
import com.example.nimban_backend.repository.TaskRepository;
import jakarta.annotation.PostConstruct;

@Component
@ConditionalOnProperty(name = "app.data-loader.enabled", havingValue = "true", matchIfMissing = false)
public class DataLoader {

        private final TaskRepository taskRepository;

        private final TaskColumnRepository taskColumnRepository;

        private ProjectRepository projectRepository;
        private CustomerRepository customerRepository;
        private RoleRepository roleRepository;
        private PasswordEncoder passwordEncoder;

        public DataLoader(CustomerRepository customerRepository, ProjectRepository projectRepository,
                        TaskColumnRepository taskColumnRepository, TaskRepository taskRepository,
                        RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
                this.customerRepository = customerRepository;
                this.projectRepository = projectRepository;
                this.taskColumnRepository = taskColumnRepository;
                this.taskRepository = taskRepository;
                this.roleRepository = roleRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @PostConstruct
        public void loadData() {
                // Clear the database here
                // customerRepository.deleteAll();

                // Initialize roles
                Role userRole = roleRepository.save(new Role(AppRole.ROLE_USER));
                Role adminRole = roleRepository.save(new Role(AppRole.ROLE_ADMIN));

                // Load customers data with encoded passwords and roles
                customerRepository.save(Customer.builder()
                                .firstName("Alan").lastName("Walker")
                                .email("alan@nimban.com")
                                .password(passwordEncoder.encode("Alan@1234"))
                                .projectsId(Arrays.asList(1L, 2L))
                                .birthYear(1980).birthMonth(1).birthDay(1)
                                .role(userRole)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .enabled(true)
                                .credentialsExpiryDate(LocalDate.of(2125, 12, 31))
                                .accountExpiryDate(LocalDate.of(2125, 12, 31))
                                .build());
                customerRepository.save(Customer.builder()
                                .firstName("Becky").lastName("Sim")
                                .email("becky@nimban.com")
                                .password(passwordEncoder.encode("Becky@1234"))
                                .projectsId(Arrays.asList(3L, 1L))
                                .birthYear(1980).birthMonth(1).birthDay(1)
                                .role(userRole)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .enabled(true)
                                .credentialsExpiryDate(LocalDate.of(2125, 12, 31))
                                .accountExpiryDate(LocalDate.of(2125, 12, 31))
                                .build());
                customerRepository.save(Customer.builder()
                                .firstName("Cindy").lastName("Lope")
                                .email("cindy@nimban.com")
                                .password(passwordEncoder.encode("Cindy@1234"))
                                .projectsId(Arrays.asList(4L, 2L))
                                .birthYear(1980).birthMonth(1).birthDay(1)
                                .role(userRole)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .enabled(true)
                                .credentialsExpiryDate(LocalDate.of(2125, 12, 31))
                                .accountExpiryDate(LocalDate.of(2125, 12, 31))
                                .build());
                customerRepository.save(Customer.builder()
                                .firstName("Danny").lastName("Smith")
                                .email("danny@nimban.com")
                                .password(passwordEncoder.encode("Danny@1234"))
                                .projectsId(Arrays.asList(5L))
                                .birthYear(1980).birthMonth(1).birthDay(1)
                                .role(userRole)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .enabled(true)
                                .credentialsExpiryDate(LocalDate.of(2125, 12, 31))
                                .accountExpiryDate(LocalDate.of(2125, 12, 31))
                                .build());

                customerRepository.save(Customer.builder()
                                .firstName("Admin").lastName("Admin")
                                .email("admin@nimban.com")
                                .password(passwordEncoder.encode("Admin@1234"))
                                .birthYear(1980).birthMonth(1).birthDay(1)
                                .role(adminRole)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .enabled(true)
                                .credentialsExpiryDate(LocalDate.of(2125, 12, 31))
                                .accountExpiryDate(LocalDate.of(2125, 12, 31))
                                .build());

                Project project1 = projectRepository.save(Project.builder().name("Monday").taskTotalId(4L).authorId(1L)
                                .hidden(false).teammatesId(Arrays.asList(1L, 2L)).build());
                Project project2 = projectRepository.save(Project.builder().name("Tuesday").taskTotalId(1L).authorId(1L)
                                .hidden(false).teammatesId(Arrays.asList(1L, 2L, 3L)).build());
                Project project3 = projectRepository.save(Project.builder().name("Wednesday").authorId(2L)
                                .taskTotalId(0L).hidden(false).teammatesId(Arrays.asList(2L)).build());
                Project project4 = projectRepository.save(Project.builder().name("Friday").authorId(3L).taskTotalId(0L)
                                .hidden(false).teammatesId(Arrays.asList(3L)).build());
                Project project5 = projectRepository.save(Project.builder().name("Sunday").authorId(4L).taskTotalId(0L)
                                .hidden(false).teammatesId(Arrays.asList(4L)).build());

                TaskColumn project1Col1 = taskColumnRepository
                                .save(TaskColumn.builder().name("Not Started").project(project1).position(0).build());
                TaskColumn project1Col2 = taskColumnRepository
                                .save(TaskColumn.builder().name("In Progress").project(project1).position(1).build());
                TaskColumn project1Col3 = taskColumnRepository.save(TaskColumn.builder().name("Done").project(project1).position(2).build());
                TaskColumn project2Col1 = taskColumnRepository
                                .save(TaskColumn.builder().name("Not Started").project(project2).position(0).build());
                TaskColumn project2Col2 = taskColumnRepository
                                .save(TaskColumn.builder().name("In Progress").project(project2).position(1).build());
                TaskColumn project2Col3 = taskColumnRepository.save(TaskColumn.builder().name("Done").project(project2).position(2).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("Not Started").project(project3).position(0).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("In Progress").project(project3).position(1).build());
                taskColumnRepository.save(TaskColumn.builder().name("Done").project(project3).position(2).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("Not Started").project(project4).position(0).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("In Progress").project(project4).position(1).build());
                taskColumnRepository.save(TaskColumn.builder().name("Done").project(project4).position(2).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("Not Started").project(project5).position(0).build());
                taskColumnRepository
                                .save(TaskColumn.builder().name("In Progress").project(project5).position(1).build());
                taskColumnRepository.save(TaskColumn.builder().name("Done").project(project5).position(2).build());

                taskRepository.save(Task.builder().name("Do laundry").project(project1).priority(1)
                                .description("To wash all white clothes").statusId(project1Col1.getId()).code("MON-0001")
                                .assigneesId(List.of(1L)).position(0).build());

                taskRepository.save(Task.builder().name("Wash dishes").project(project1).priority(2)
                                .description("Wash with detergent").statusId(project1Col1.getId()).code("MON-0002")
                                .assigneesId(List.of(1L)).position(1000).build());

                taskRepository.save(Task.builder().name("Mop floor").project(project1).priority(3)
                                .description("Mop only the living room").statusId(project1Col1.getId()).code("MON-0003")
                                .assigneesId(List.of(1L)).position(2000).build());

                taskRepository.save(Task.builder().name("Take out trash").project(project1).priority(4)
                                .description("Clear trash before 7pm").statusId(project1Col2.getId()).code("MON-0004")
                                .assigneesId(List.of(1L)).position(0).build());

                taskRepository.save(Task.builder().name("Buy grocery").project(project2).priority(1)
                                .description("Get rice, milk and bread").statusId(project2Col1.getId()).code("TUE-0001")
                                .assigneesId(List.of(1L)).position(0).build());

        }
}
