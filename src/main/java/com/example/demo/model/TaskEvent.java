package com.example.demo.model;

import com.example.demo.enums.TaskEventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TaskEvent {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long taskId;
    private TaskEventType eventType;
    private String eventPayload;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
}