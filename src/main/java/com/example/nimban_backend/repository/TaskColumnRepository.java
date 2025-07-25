package com.example.nimban_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nimban_backend.entity.TaskColumn;

public interface TaskColumnRepository extends JpaRepository<TaskColumn, Long> {
    
}
