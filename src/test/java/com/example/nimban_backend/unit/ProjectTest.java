package com.example.nimban_backend.unit;

import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.Project;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        Project project = new Project();
        project.setName("Vision Tracker");
        project.setHidden(true);
        project.setAuthorId(99L);
        project.setTaskTotalId(17L);

        assertThat(project.getName()).isEqualTo("Vision Tracker");
        assertThat(project.getHidden()).isTrue();
        assertThat(project.getAuthorId()).isEqualTo(99L);
        assertThat(project.getTaskTotalId()).isEqualTo(17L);
    }
}
