package com.example.demo.service;

import com.example.demo.enums.TaskStatus;
import com.example.demo.exception.TaskException;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//some changes to be displayed in PR

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public Task create(Task request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new TaskException("Title is required", HttpStatus.BAD_REQUEST);
        }

        if (request.getAuthor() == null) {
            throw new TaskException("Author is required", HttpStatus.BAD_REQUEST);
        }

        if (request.getAssignee() == null) {
            throw new TaskException("Assignee is required", HttpStatus.BAD_REQUEST);
        }
        request.setStatus(TaskStatus.TO_DO);
        request.setCreatedAt(LocalDateTime.now());
        return repository.save(request);
    }

    public Task get(long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new TaskException("Task not found", HttpStatus.NOT_FOUND));
    }

    public Task update(long id, Task request) {

        Task existing = get(id);

        if (request.getTitle() != null) {
            if (request.getTitle().isBlank()) {
                throw new TaskException("Title cannot be empty", HttpStatus.BAD_REQUEST);
            }
            existing.setTitle(request.getTitle());
        }

        if (request.getDescription() != null)
            existing.setDescription(request.getDescription());

        if (request.getDeadline() != null)
            existing.setDeadline(request.getDeadline());

        if (request.getAssignee() != 0)
            existing.setAssignee(request.getAssignee());

        if (request.getStatus() != null)
            existing.setStatus(request.getStatus());

        existing.setUpdatedAt(LocalDateTime.now());

        return repository.update(existing);
    }

    public List<Task> getAll(TaskStatus status, Long assignee) {
        return repository.findAllFiltered(
                status != null ? status.name() : null,
                assignee
        );
    }

    public boolean existsActiveByAssignee(Long userId) {
        return repository.existsActiveByAssignee(userId);
    }
}
