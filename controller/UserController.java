package com.example.tasktracker.controller;

import com.example.tasktracker.dto.UserDTOs;
import com.example.tasktracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOs.Response createUser(@Valid @RequestBody UserDTOs.Create dto) {
        return userService.createUser(dto);
    }
}
