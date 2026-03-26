package com.example.demo.service.permission;

import com.example.demo.client.UserClient;
import com.example.demo.exception.TaskException;
import com.example.demo.model.Task;
import com.example.demo.model.UpdateTaskRequest;
import com.example.demo.model.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskPermissionService {
    private final UserClient userClient;

    public void checkAssigneeChange(Task existing, UpdateTaskRequest request) {

        if (existing.getAssignee() != null &&
            existing.getAssignee().equals(request.getAssignee())) {
            return;
        }

        if (request.getEditorId() == null) {
            throw new TaskException("Editor id is required", HttpStatus.BAD_REQUEST);
        }

        String role = userClient.getUserById(request.getEditorId()).getRole();

        if (!"MANAGER".equals(role)) {
            throw new TaskException("Only MANAGER can change assignee", HttpStatus.FORBIDDEN);
        }
    }
}
