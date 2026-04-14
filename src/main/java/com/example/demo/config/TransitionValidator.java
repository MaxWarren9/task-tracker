package com.example.demo.config;

import com.example.demo.enums.TaskStatus;
import com.example.demo.exception.TaskException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TransitionValidator {

    public void validate(TaskStatus currentStatus, TaskStatus newStatus) {
        if (newStatus == null || currentStatus == newStatus) {
            return;
        }

        boolean isValid = switch (currentStatus) {
            case TO_DO -> newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.DELETE;
            case IN_PROGRESS -> newStatus == TaskStatus.DONE || newStatus == TaskStatus.DELETE;
            case DONE -> newStatus == TaskStatus.DELETE;
            case DELETE -> false;
        };

        if (!isValid) {
            throw new TaskException(
                    "Invalid status transition: " + currentStatus + " -> " + newStatus,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}