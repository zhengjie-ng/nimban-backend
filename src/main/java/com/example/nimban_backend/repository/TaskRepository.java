package com.example.nimban_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nimban_backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
