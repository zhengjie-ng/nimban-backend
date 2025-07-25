package com.example.nimban_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nimban_backend.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
