package com.example.tasktracker.controller;

import com.example.tasktracker.dto.ProjectDTOs;
import com.example.tasktracker.dto.TaskDTOs;
import com.example.tasktracker.service.ProjectService;
import com.example.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTOs.Response createProject(@Valid @RequestBody ProjectDTOs.Create dto) {
        return projectService.createProject(dto);
    }

    @GetMapping
    public Page<ProjectDTOs.Response> getAllProjects(Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @GetMapping("/{id}")
    public ProjectDTOs.Response getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTOs.Response addTask(@PathVariable Long projectId, @Valid @RequestBody TaskDTOs.Create dto) {
        return taskService.addTask(projectId, dto);
    }

    @GetMapping("/{projectId}/tasks")
    public Page<TaskDTOs.Response> getProjectTasks(@PathVariable Long projectId, Pageable pageable) {
        return taskService.getTasksByProject(projectId, pageable);
    }
}
