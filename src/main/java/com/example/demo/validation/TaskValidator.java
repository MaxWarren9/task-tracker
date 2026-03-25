package com.example.demo.validation;

import com.example.demo.exception.TaskException;
import com.example.demo.model.Task;
import com.example.demo.model.UpdateTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
//
//@Component
//public class TaskValidator {
//
//    public void validateForCreate(Task request) {
//        validateTitle(request);
//        validateAuthor(request);
//        validateAssignee(request);
//        validateDeadline(request.getDeadline());
//    }
//
//    public void validateForUpdate(UpdateTaskRequest request) {
//        if (request.getTitle() != null && request.getTitle().isBlank()) {
//            throw new TaskException("Title cannot be blank", HttpStatus.BAD_REQUEST);
//        }
//
//        validateDeadline(request.getDeadline());
//    }
//
//    private void validateTitle(Task request) {
//        if (request.getTitle() == null || request.getTitle().isBlank()) {
//            throw new TaskException("Title is required", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private void validateAuthor(Task request) {
//        if (request.getAuthor() == null) {
//            throw new TaskException("Author is required", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private void validateAssignee(Task request) {
//        if (request.getAssignee() == null) {
//            throw new TaskException("Assignee is required", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private void validateDeadline(LocalDate deadline) {
//        if (deadline != null && deadline.isBefore(LocalDate.now())) {
//            throw new TaskException("Deadline cannot be in the past", HttpStatus.BAD_REQUEST);
//        }
//    }
//}

@Component
public class TaskValidator {

    public void validateForCreate(Task request) {
        validateTitle(request);
        validateAuthor(request);
        validateAssignee(request);
        validateDeadline(request.getDeadline());
    }

    public void validateForUpdate(UpdateTaskRequest request) {
        if (request.getTitle() != null && request.getTitle().isBlank()) {
            throw new TaskException("Title cannot be blank", HttpStatus.BAD_REQUEST);
        }

        validateDeadline(request.getDeadline());
    }

    private void validateTitle(Task request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new TaskException("Title is required", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateAuthor(Task request) {
        if (request.getAuthor() == null) {
            throw new TaskException("Author is required", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateAssignee(Task request) {
        if (request.getAssignee() == null) {
            throw new TaskException("Assignee is required", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDeadline(LocalDate deadline) {
        if (deadline != null && deadline.isBefore(LocalDate.now())) {
            throw new TaskException("Deadline cannot be in the past", HttpStatus.BAD_REQUEST);
        }
    }
}