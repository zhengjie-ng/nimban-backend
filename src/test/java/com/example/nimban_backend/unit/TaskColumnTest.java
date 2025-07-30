package com.example.nimban_backend.unit;

import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.TaskColumn;

import static org.assertj.core.api.Assertions.assertThat;

class TaskColumnTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        TaskColumn column = new TaskColumn();
        column.setName("In Progress");
        column.setPosition(99);

        assertThat(column.getName()).isEqualTo("In Progress");
        assertThat(column.getPosition()).isEqualTo(99);
    }
}