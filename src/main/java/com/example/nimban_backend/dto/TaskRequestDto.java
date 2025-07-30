package com.example.nimban_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskRequestDto {
    private String name;
    private List<Long> assigneesId;
    private String code;
    private Long priority;
    private Long statusId;
    private String description;
    private String sortedTimeStamp;
    private Integer position;
}
