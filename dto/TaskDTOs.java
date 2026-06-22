package com.example.tasktracker.dto;

import com.example.tasktracker.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TaskDTOs {
    public record Create(
        @NotBlank(message = "Task title is mandatory") String title,
        String description,
        @NotNull(message = "Task status is required") TaskStatus status,
        Long assigneeId
    ) {}

    public record Response(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long projectId,
        UserDTOs.Response assignee,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
    ) {}
}
