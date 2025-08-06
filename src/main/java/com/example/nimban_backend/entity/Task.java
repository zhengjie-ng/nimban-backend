package com.example.nimban_backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task")

public class Task {
    // Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Task name must not be blank")
    // @Size(max = 100, message = "Task name must not exceed 100 characters")
    @Size(min = 3, max = 30, message = "Task name must be between 3 and 30 characters")
    @Column(name = "name")
    private String name;

    // @Transient
    // @Size(min = 1, message = "There must be at least one assignee")
    // private List<@NotNull(message = "Assignee ID cannot be null") Long> assigneesId;
    @Column(name = "assignees_Id")
    private List<Long> assigneesId;

    @Size(max = 10, message = "Code must not exceed 10 characters")
    @Column(name = "code")
    private String code;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority cannot be greater than 5")
    @NotNull(message = "Priority must not be null")
    @Column(name = "priority")
    private Integer priority;

    @Min(value = 1, message = "Status ID must be positive")
    @NotNull(message = "Status ID must not be null")
    @Column(name = "status_id")
    private Long statusId;

    // @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description")
    private String description;

    @Column(name = "sorted_time_stamp")
    private String sortedTimeStamp;

    @Min(value = 0, message = "Position must be non-negative")
    @NotNull(message = "Position must not be null")
    @Column(name = "position")
    private Integer position;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(lombok.AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Setter(lombok.AccessLevel.NONE)
    private LocalDateTime updatedAt;

    // triggered before an entity is saved
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    // triggered before an entity is updated
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    

    // @NotNull(message = "Project must not be null")
    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

}
