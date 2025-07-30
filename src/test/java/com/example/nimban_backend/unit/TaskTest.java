package com.example.nimban_backend.unit;

import org.junit.jupiter.api.Test;

import com.example.nimban_backend.entity.Task;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void shouldSetFieldsCorrectly() {
        Task task = new Task();
        task.setName("Implement JWT Filter");
        task.setCode("T-JWT-FILTER");
        task.setPosition(456);

        assertThat(task.getName()).isEqualTo("Implement JWT Filter");
        assertThat(task.getCode()).isEqualTo("T-JWT-FILTER");
        assertThat(task.getPosition()).isEqualTo(456L);
    }
}