package com.example.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ProjectDTOs {
    public record Create(
        @NotBlank(message = "Project name is mandatory") String name,
        String description
    ) {}

    public record Response(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
    ) {}
}
