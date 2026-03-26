package com.example.demo.model;

import com.example.demo.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private LocalDate deadline;
    private Long assignee;
    private TaskStatus status;
    private Long editorId;
}
