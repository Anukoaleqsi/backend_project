package com.example.tasktracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTOs {
    public record Create(
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Name is required") String name
    ) {}

    public record Response(
        Long id,
        String email,
        String name
    ) {}
}
