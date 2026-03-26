package com.example.demo.service;

import com.example.demo.enums.TaskEventType;
import com.example.demo.model.Task;
import com.example.demo.model.TaskEvent;
import com.example.demo.repository.TaskEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEventService {

    private final TaskEventRepository repository;

    public void saveCreatedEvent(Task task) {
        repository.save(
                TaskEvent.builder()
                         .taskId(task.getId())
                         .eventType(TaskEventType.CREATED)
                         .eventPayload("Task created")
                         .createdAt(LocalDateTime.now())
                         .build()
        );
    }

    public void saveStatusChangedEvent(Long taskId, String oldStatus, String newStatus) {
        repository.save(
                TaskEvent.builder()
                         .taskId(taskId)
                         .eventType(TaskEventType.STATUS_CHANGED)
                         .eventPayload("Status changed from " + oldStatus + " to " + newStatus)
                         .createdAt(LocalDateTime.now())
                         .build()
        );
    }

    public void saveAssigneeChangedEvent(Long taskId, Long oldAssignee, Long newAssignee) {
        repository.save(
                TaskEvent.builder()
                         .taskId(taskId)
                         .eventType(TaskEventType.ASSIGNEE_CHANGED)
                         .eventPayload("Assignee changed from " + oldAssignee + " to " + newAssignee)
                         .createdAt(LocalDateTime.now())
                         .build()
        );
    }

    public void saveUpdatedEvent(Long taskId, String payload) {
        repository.save(
                TaskEvent.builder()
                         .taskId(taskId)
                         .eventType(TaskEventType.UPDATED)
                         .eventPayload(payload)
                         .createdAt(LocalDateTime.now())
                         .build()
        );
    }

    public List<TaskEvent> getHistory(Long taskId) {
        return repository.findByTaskId(taskId);
    }
}
