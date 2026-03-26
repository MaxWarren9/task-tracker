package com.example.demo.controller;

import com.example.demo.enums.TaskStatus;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Задача")
public class TaskController {

    private final TaskService service;

    @PostMapping
    @Operation(summary = "Создать задачу")
    public Task create(@RequestBody Task request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по id")
    public Task get(@PathVariable long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить задачу")
    public Task update(@PathVariable long id,
                       @RequestBody Task request) {
        return service.update(id, request);
    }

    @GetMapping
    @Operation(summary = "Получить все задачи")
    public List<Task> getAll(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long assignee) {

        return service.getAll(status, assignee);
    }

    @GetMapping("/exists/assignee/{userId}")
    public boolean existsByAssignee(@PathVariable Long userId) {
        return service.existsActiveByAssignee(userId);
    }
}