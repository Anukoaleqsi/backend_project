package com.example.tasktracker.service;

import com.example.tasktracker.dto.TaskDTOs;
import com.example.tasktracker.dto.UserDTOs;
import com.example.tasktracker.exception.ResourceNotFoundException;
import com.example.tasktracker.model.Project;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.User;
import com.example.tasktracker.repository.ProjectRepository;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public TaskDTOs.Response addTask(Long projectId, TaskDTOs.Create dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        task.setProject(project);

        if (dto.assigneeId() != null) {
            User user = userRepository.findById(dto.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User/Assignee not found"));
            task.setAssignee(user);
        }

        Task saved = taskRepository.saveAndFlush(task);
        return mapToResponse(saved);
    }

    @Transactional
    public TaskDTOs.Response updateTask(Long taskId, TaskDTOs.Create dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found"));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());

        if (dto.assigneeId() != null) {
            User user = userRepository.findById(dto.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User/Assignee not found"));
            task.setAssignee(user);
        } else {
            task.setAssignee(null);
        }

        Task updated = taskRepository.saveAndFlush(task);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task with ID " + taskId + " not found");
        }
        taskRepository.deleteById(taskId);
    }

    @Transactional(readOnly = true)
    public Page<TaskDTOs.Response> getTasksByProject(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found");
        }
        return taskRepository.findByProjectId(projectId, pageable).map(this::mapToResponse);
    }

    private TaskDTOs.Response mapToResponse(Task task) {
        UserDTOs.Response userResp = null;
        if (task.getAssignee() != null) {
            userResp = new UserDTOs.Response(task.getAssignee().getId(), task.getAssignee().getEmail(), task.getAssignee().getName());
        }
        return new TaskDTOs.Response(
                task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getProject().getId(), userResp, task.getCreatedAt(), task.getLastModifiedAt()
        );
    }
}
